package com.yrnet.viewweb.business.video.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.bill.entity.UserAccount;
import com.yrnet.viewweb.business.bill.service.IUserAccountService;
import com.yrnet.viewweb.business.login.entity.WxUser;
import com.yrnet.viewweb.business.login.service.WxUserService;
import com.yrnet.viewweb.business.video.dto.*;
import com.yrnet.viewweb.business.video.entity.ParseLog;
import com.yrnet.viewweb.business.video.entity.Ranking;
import com.yrnet.viewweb.business.video.mapper.ParseLogMapper;
import com.yrnet.viewweb.business.video.service.IParseLogService;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.properties.ViewWebProperties;
import com.yrnet.viewweb.common.service.ISeqService;
import com.yrnet.viewweb.common.utils.DateUtil;
import com.yrnet.viewweb.common.utils.FileUtil;
import com.yrnet.viewweb.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dengbp
 */
@Service
@Slf4j
public class ParseLogServiceImpl extends ServiceImpl<ParseLogMapper, ParseLog> implements IParseLogService {

    @Autowired
    private ViewWebProperties yinXXProperties;
    @Autowired
    private WxUserService  wxUserServiceImpl;
    @Autowired
    private ParseLogMapper parseLogMapper;
    @Autowired
    private ISeqService seqService;
    @Resource
    private IUserAccountService userAccountService;


    @Override
    public VideoParseRespDto parseVideo(VideoParseReqDto reqDto) throws DocumentException {
        String result;
        try {
            List<ParseLog> parseLogs = this.list(new LambdaQueryWrapper<ParseLog>().eq(ParseLog::getParseUrl,reqDto.getParseUrl()));
            if (parseLogs != null && !parseLogs.isEmpty()){
                ParseLog log = parseLogs.get(0);
                VideoParseRespDto dto = this.parser2(log.getParseResult());
                dto.setVideoId(log.getId());
                dto.setUrl(log.getActiveUrl());
                dto.setAllow(0);
                return dto;
            }
            result = HttpUtil.sendPost(yinXXProperties.getVideo_parse_url()+reqDto.getParseUrl(),null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DocumentException(e.getMessage());
        }
        VideoParseRespDto respDto = this.handle(reqDto,result);
        try {
            sync(respDto);
        } catch (Exception e) {
            log.error("视频同步失败",e);
            return respDto;
        }
        return respDto;
    }

    private void sync(VideoParseRespDto respDto)throws Exception{
        if (StringUtils.isBlank(respDto.getImg())){
            return;
        }
        MomentDto dto = new MomentDto();
        dto.setPreviewUrl(respDto.getImg());
        dto.setUrl(respDto.getUrl());
        dto.setShowWord(regex(respDto.getTitle()+" "));
        log.info("同步结果：{}",HttpUtil.sendPost(yinXXProperties.getApp_moment_url(),JSONObject.toJSONString(dto)));
    }
    static Pattern p=Pattern.compile("(#.+?\\s)|(@.+?\\s)");
    private String regex(String source){
        if (StringUtils.isBlank(source)){
            return source;
        }
        Matcher m=p.matcher(source);
        return m.replaceAll("").trim();
    }

    /**
     * Description 判断是否允许下载
     * @param userId
     * @return boolean
     * @Author dengbp
     * @Date 9:51 AM 6/29/21
     **/

    @Override
    public boolean allowDownload(String userId) throws DocumentException {
        UserAccount userAccount = userAccountService.getByUserId(userId);
        if (userAccount == null){
            return false;
        }
        if (userAccount.getAbleTimes() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void updateDownloadRecord(VideoDownloadReqDto dto) throws DocumentException {
        userAccountService.updateByUserId(dto.getUserId());
        ParseLog log  = this.getById(dto.getVideoId());
        if (log != null) {
            LambdaUpdateWrapper<ParseLog> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(ParseLog::getDownloadCount,log.getDownloadCount()+1);
            updateWrapper.set(ParseLog::getAllow,0);
            updateWrapper.eq(ParseLog::getId,dto.getVideoId());
            this.update(updateWrapper);
        }
    }

    @Override
    public List<Ranking> ranking() throws DocumentException {
        return parseLogMapper.ranking();
    }

    @Override
    public List<ParseLogRespDto> queryLog(ParseLogReqDto dto) throws DocumentException {
        LambdaQueryWrapper<ParseLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParseLog::getUserId,dto.getUserId());
        queryWrapper.orderByDesc(ParseLog::getCreateTime);
        List<ParseLog> logs = this.list(queryWrapper);
        UserAccount userAccount = userAccountService.getByUserId(dto.getUserId());
        Integer allow = 1;
        if (userAccount != null && userAccount.getAbleTimes()>0){
            allow = 2;
        }
        return this.returnVideoList(logs,allow);
    }

    private List<ParseLogRespDto> returnVideoList(List<ParseLog> logs,Integer allow ){
        List<ParseLogRespDto> respDtos = new ArrayList<>();
        if(logs == null){
            return respDtos;
        }
        this.transToResp(logs,respDtos,allow);
        return respDtos;
    }

    @Override
    public List<ParseLogRespDto>  onlineList(QueryRequestPage requestPage)throws DocumentException {
        Page<ParseLog> page = new Page<>(requestPage.getPageNum(), requestPage.getPageSize());
        List<ParseLog> logs = baseMapper.selectPage(page,new LambdaQueryWrapper<ParseLog>().ne(ParseLog::getImg,"illegal").orderByDesc(ParseLog::getCreateTime)).getRecords();
        return this.returnVideoList(logs,1);
    }

    @Override
    public void refreshImg() {
        List<ParseLog> logs = list();
        logs.forEach(l->{
            VideoParseRespDto reqDto = parser2(l.getParseResult());
            String fileName = System.currentTimeMillis()+".jpeg";
            log.info("download url:{},id:{}",reqDto.getImg(),l.getId());
            if (!StringUtils.equalsIgnoreCase("0",reqDto.getCode())){
                return;
            }
            HttpUtil.download(reqDto.getImg(),yinXXProperties.getVideo_path()+l.getUserId(), fileName);
            l.setImg(yinXXProperties.getUrl_base()+"/"+ l.getUserId() + "/" + fileName);
            update(new LambdaUpdateWrapper<ParseLog>().set(ParseLog::getImg,l.getImg()).eq(ParseLog::getId,l.getId()));
            log.info("update id:{} success",l.getId());
        });
    }

    @Override
    public MultipartFileRespDto saveFile(MultipartFile file, String relationId, String openId) throws DocumentException {
        String originName = file.getOriginalFilename();
        log.info("begin upload file[{}]",originName);
        String path = FileUtil.getFilePath(yinXXProperties,originName);
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            MultipartFileRespDto respDto = new MultipartFileRespDto();
            String extName = originName.substring(originName.lastIndexOf(".") + 1);
            String fileName = System.currentTimeMillis()+"."+extName;
            WxUser user = wxUserServiceImpl.queryByOpenId(openId);
            VideoParseReqDto reqDto = new VideoParseReqDto();
            reqDto.setUserId(openId);
            if (StringUtils.isBlank(relationId)){
                respDto.setDocUrl(yinXXProperties.getUrl_base()+"/"+ user.getWxUserId() + "/" + fileName);
                Long id = doLog(reqDto,null,respDto.getDocUrl(),null,user);
                respDto.setDocId(id);
            }else {
                respDto.setDocUrl(yinXXProperties.getUrl_base()+"/"+ user.getWxUserId() + "/" + fileName);
                respDto.setDocId(Long.parseLong(relationId));
                ParseLog  log = this.getById(respDto.getDocId());
                if (log != null){
                    respDto.setDocUrl(log.getActiveUrl());
                }
            }
            log.info("upload file[{}]success",originName);
            return respDto;
        } catch (Exception e) {
            log.error("上传失败",e);
            throw new DocumentException(e.getMessage());
        }
    }

    @Override
    public void release(MultipartInfoRequestDto dto) throws DocumentException {

    }

    private void transToResp(List<ParseLog> sources,List<ParseLogRespDto> response,Integer allow ){
        Map<String,Byte> tmp = new HashMap<>();
        sources.stream().forEach(e->{
            if (tmp.containsKey(e.getParseUrl())){
                return;
            }
            tmp.put(e.getParseUrl(),Byte.parseByte("0"));
            ParseLogRespDto respDto = new ParseLogRespDto();
            respDto.setCreateTime(e.getCreateTime());
            respDto.setState(0);
            respDto.setUrl(e.getActiveUrl());
            respDto.setImg(e.getImg());
            JSONObject root = JSONObject.parseObject(e.getParseResult());
            if (root == null){
                respDto.setState(1);
            }else {
                JSONObject body = root.getJSONObject("body");
                if (body == null){
                    respDto.setState(1);
                }else{
                    String title = body.getString("text");
                    respDto.setTitle(title);
                }
            }
            respDto.setAllow(e.getAllow());
            if (respDto.getAllow()==1){
                respDto.setAllow(allow);
            }
            respDto.setVideoId(e.getId());
            response.add(respDto);
        });
    }



    private VideoParseRespDto parser2(String result) {
        VideoParseRespDto respDto = new VideoParseRespDto();
        respDto.setCode("-1");
        respDto.setAllow(1);
        if (StringUtils.isBlank(result)) {
            return respDto;
        }
        JSONObject root = JSONObject.parseObject(result);
        if (root == null) {
            return respDto;
        }
        respDto.setCode(StringUtils.isBlank(root.getString("code"))?"-1":root.getString("code"));
        if (StringUtils.equalsIgnoreCase("0",respDto.getCode())){
            JSONObject body = root.getJSONObject("body");
            if (body == null){
                return respDto;
            }
            String title = body.getString("text");
            JSONObject videoInfo = body.getJSONObject("video_info");
            if (videoInfo == null){
                return respDto;
            }
            respDto.setTitle(title);
            respDto.setImg(videoInfo.getString("cover"));
            respDto.setDownloadUrl(videoInfo.getString("url"));
        }
        return respDto;
    }

    private VideoParseRespDto handle(VideoParseReqDto reqDto,String result){
        VideoParseRespDto respDto = this.parser2(result);
        if (StringUtils.equalsIgnoreCase("0",respDto.getCode())){
            String fileName = System.currentTimeMillis()+".mp4";
            WxUser user = wxUserServiceImpl.queryByOpenId(reqDto.getUserId());
            respDto.setUrl(yinXXProperties.getUrl_base()+"/"+ user.getWxUserId() + "/" + fileName);
            /** 视频下载 */
            HttpUtil.download(respDto.getDownloadUrl(),yinXXProperties.getVideo_path()+user.getWxUserId(), fileName);
            /** 封面下载 */
            fileName = System.currentTimeMillis()+".jpeg";
            HttpUtil.download(respDto.getImg(),yinXXProperties.getVideo_path()+reqDto.getUserId(), fileName);
            respDto.setImg(yinXXProperties.getUrl_base()+"/"+ reqDto.getUserId() + "/" + fileName);
            Long id = this.doLog(reqDto,result,respDto.getUrl(),respDto.getImg(),user);
            respDto.setVideoId(id);
        }
        if (this.allowDownload(reqDto.getUserId())){
            respDto.setAllow(0);
        }
        respDto.setDownloadUrl("");
        return respDto;
    }


    private Long doLog(VideoParseReqDto reqDto,String result,String url,String img,WxUser user){
        ParseLog log = new ParseLog();
        log.setId(seqService.getSeq());
        log.setUserId(reqDto.getUserId());
        log.setActiveUrl(url);
        log.setParseUrl(reqDto.getParseUrl());
        log.setParseResult(result);
        log.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        log.setImg(StringUtils.isBlank(img)?"illegal":img);
        if (user != null) {
            log.setUserAvatarUrl(user.getAvatarUrl());
        }
        this.save(log);
        return log.getId();
    }

    private void setPreImg(Long id,String imgUrl){
        update(new LambdaUpdateWrapper<ParseLog>().set(ParseLog::getImg,imgUrl).eq(ParseLog::getId,id));
    }
}

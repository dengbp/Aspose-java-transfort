package com.yrnet.appweb.business.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.appweb.business.bill.dto.VipInfoResDto;
import com.yrnet.appweb.business.custom.service.IVipInfoService;
import com.yrnet.appweb.business.file.bo.FileConvertBo;
import com.yrnet.appweb.business.file.dto.ConvertLogRequest;
import com.yrnet.appweb.business.file.dto.ConvertLogResponse;
import com.yrnet.appweb.business.file.dto.TransferRequest;
import com.yrnet.appweb.business.file.dto.ConvertResponse;
import com.yrnet.appweb.business.file.entity.ConvertFreeTrialRecord;
import com.yrnet.appweb.business.file.entity.ConvertLog;
import com.yrnet.appweb.business.file.mapper.ConvertLogMapper;
import com.yrnet.appweb.business.file.service.IConvertFreeTrialRecordService;
import com.yrnet.appweb.business.file.service.IConvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.appweb.business.file.service.TransferApi;
import com.yrnet.appweb.business.login.entity.WxUser;
import com.yrnet.appweb.business.login.service.WxUserService;
import com.yrnet.appweb.common.exception.DocumentException;
import com.yrnet.appweb.common.http.TransferResponse;
import com.yrnet.appweb.common.properties.ViewWebProperties;
import com.yrnet.appweb.common.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class ConvertServiceImpl extends ServiceImpl<ConvertLogMapper, ConvertLog> implements IConvertService {

    @Autowired
    private ViewWebProperties viewWebProperties;
    @Autowired
    private WxUserService wxUserServiceImpl;
    @Autowired
    private TransferApi transferApi;
    @Autowired
    private ConvertLogMapper convertLogMapper;
    @Resource
    private IVipInfoService vipInfoService;
    @Resource
    private IConvertFreeTrialRecordService convertFreeTrialRecordService;

    private static  Integer FAIL = 2;



    @Override
    public List<ConvertLogResponse> queryLog(ConvertLogRequest dto) throws DocumentException {
        LambdaQueryWrapper<ConvertLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConvertLog::getUserOpenId,dto.getOpenId());
        queryWrapper.orderByDesc(ConvertLog::getCreateTime);
        List<ConvertLog> logs = this.list(queryWrapper);
        WxUser user = wxUserServiceImpl.queryByOpenId(dto.getOpenId());
        return this.returnFilList(logs,user);
    }

    @Override
    public ConvertLogResponse transfer(FileConvertBo bo) throws DocumentException {
        try {
            StringBuffer filePaths = new StringBuffer();
            for (File f : bo.getFiles()){
                filePaths.append(f.getAbsolutePath()).append(",");
            }
            ConvertLog log = bo.builderEntity();
            log.setFilePath(filePaths.substring(0,filePaths.length() - 1));
            convertLogMapper.insert(log);
            ConvertLogResponse response = log.transformToRes();
            response.setCreateTime(log.getCreateTime());
            TransferResponse tfResponse = transferApi.convert(TransferRequest.builder().fileId(log.getId()).filePath(log.getFilePath()).fileName(log.getFileName()).toType(log.getToType()).build());
            if(!tfResponse.isNormal()){
                log.setState(FAIL);
                this.updateById(log);
                response.setState(FAIL);
                response.setFileSize(0L);
                return response;
            }
            ConvertResponse tr = JSONObject.parseObject(JSONObject.toJSONString(tfResponse.get("data")),ConvertResponse.class);
            log.setNewFilePath(tr.getFilePath());
            log.setNewFileName(tr.getFileName());
            log.setNewFileSize(tr.getFileSize());
            log.setId(tr.getFileId());
            log.setState(tr.getState());
            this.updateById(log);
            List<String> paths = Arrays.asList(tr.getFilePath().split(","));
            StringBuffer newFilePaths = new StringBuffer();
            paths.forEach(path->newFilePaths.append(viewWebProperties.getUrl_base()).append(path.substring(path.lastIndexOf("/"))).append(","));
            response.setUrl(newFilePaths.substring(0,newFilePaths.length() - 1));
            response.setState(tr.getState());
            response.setFileName(tr.getFileName());
            response.setFileId(tr.getFileId());
            response.setFileSize(tr.getFileSize());
            if (!this.isVipUser(bo.getOpenId())){
                ConvertFreeTrialRecord record = ConvertFreeTrialRecord.builder()
                        .setFilePath(log.getFilePath())
                        .setNewFilePath(log.getNewFilePath())
                        .setUserOpenId(log.getUserOpenId())
                        .setToType(log.getToType())
                        .setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
                convertFreeTrialRecordService.save(record);
            }
            return response;
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new DocumentException(e.getMessage());
        }
    }

    @Override
    public boolean canFreeTrial(String openId) throws DocumentException {
        int count = convertFreeTrialRecordService.count(new LambdaQueryWrapper<ConvertFreeTrialRecord>().eq(ConvertFreeTrialRecord::getUserOpenId,openId));
        return count>0?false:true;
    }

    @Override
    public boolean vipIsExpired(String openId) throws DocumentException {
        VipInfoResDto vipInf = vipInfoService.getVipInf(openId);
        try {
            Date expired =  DateUtil.stringToDate(vipInf.getExpireDate().concat("235959"),DateUtil.FULL_TIME_PATTERN);
            Date current = new Date();
            return current.after(expired);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isVipUser(String openId){
        VipInfoResDto vipInf = vipInfoService.getVipInf(openId);
        if (vipInf != null){
            return true;
        }
        return false;
    }


    private List<ConvertLogResponse> returnFilList(List<ConvertLog> logs,WxUser user){
        List<ConvertLogResponse> response = new ArrayList<>();
        if(logs == null){
            return response;
        }
        logs.forEach(l->{
            List<String> paths = Arrays.asList(l.getNewFilePath().split(","));
            StringBuffer newFilePaths = new StringBuffer();
            paths.forEach(path->newFilePaths.append(viewWebProperties.getUrl_base()).append(path.substring(path.lastIndexOf("/"))).append(","));
            response.add(ConvertLogResponse.builder()
                            .allow(0)
                            .createTime(l.getCreateTime())
                            .fileId(l.getId())
                            .url(newFilePaths.substring(0,newFilePaths.length() - 1))
                            .useName(user.getWxUserName())
                            .fileName(l.getFileName())
                            .fileSize(l.getFileSize())
                            .state(l.getState()
                            ).build());
                }

        );
        return response;
    }
}

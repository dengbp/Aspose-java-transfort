package com.yrnet.pcweb.business.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.pcweb.business.custom.service.IVipInfoService;
import com.yrnet.pcweb.business.file.bo.FileConvertBo;
import com.yrnet.pcweb.business.file.dto.ConvertLogRequest;
import com.yrnet.pcweb.business.file.dto.ConvertLogResponse;
import com.yrnet.pcweb.business.file.dto.TransferRequest;
import com.yrnet.pcweb.business.file.dto.ConvertResponse;
import com.yrnet.pcweb.business.file.entity.ConvertFreeTrialRecord;
import com.yrnet.pcweb.business.file.entity.ConvertLog;
import com.yrnet.pcweb.business.file.mapper.ConvertLogMapper;
import com.yrnet.pcweb.business.file.service.IConvertFreeTrialRecordService;
import com.yrnet.pcweb.business.file.service.IConvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.pcweb.business.file.service.TransferApi;
import com.yrnet.pcweb.business.login.entity.WxUser;
import com.yrnet.pcweb.business.login.service.WxUserService;
import com.yrnet.pcweb.common.exception.DocumentException;
import com.yrnet.pcweb.common.http.TransferResponse;
import com.yrnet.pcweb.common.properties.ViewWebProperties;
import com.yrnet.pcweb.common.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static int FREE_TIMES = 3;



    @Override
    public List<ConvertLogResponse> queryLog(ConvertLogRequest dto) throws DocumentException {
        LambdaQueryWrapper<ConvertLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConvertLog::getUserOpenId,dto.getOpenId()).isNotNull(ConvertLog::getNewFilePath);
        queryWrapper.orderByDesc(ConvertLog::getCreateTime);
        List<ConvertLog> logs = this.list(queryWrapper);
        WxUser user = wxUserServiceImpl.queryByOpenId(dto.getOpenId());
        if (user == null || logs.isEmpty()){
            return new ArrayList<>();
        }
        return this.returnFileList(logs,user);
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
            if (!vipInfoService.isVipUser(bo.getOpenId())){
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
        return count>FREE_TIMES?false:true;
    }

    private List<ConvertLogResponse> returnFileList(List<ConvertLog> logs,WxUser user){
        List<ConvertLogResponse> response = new ArrayList<>();
        if(logs == null){
            return response;
        }
        for (ConvertLog l : logs){
            if (StringUtils.isBlank(l.getNewFilePath())){
                continue;
            }
            List<String> paths = Arrays.asList(l.getNewFilePath().trim().split(","));
            StringBuffer newFilePaths = new StringBuffer();
            paths.forEach(path->newFilePaths.append(viewWebProperties.getUrl_base()).append(path.substring(path.lastIndexOf("/"))).append(","));
                    try {
                        response.add(ConvertLogResponse.builder()
                                        .allow(0)
                                        .createTime(l.getCreateTime())
                                        .createTimeStr(DateUtil.getDateFormat(DateUtil.stringToDate(l.getCreateTime()+"",DateUtil.FULL_TIME_PATTERN),DateUtil.FULL_TIME_SPLIT_PATTERN2))
                                        .fileId(l.getId())
                                        .url(newFilePaths.substring(0,newFilePaths.length() - 1))
                                        .useName(user.getWxUserName())
                                        .fileName(StringUtils.isBlank(l.getNewFileName())?l.getFileName():l.getNewFileName())
                                        .fileSize(l.getFileSize())
                                        .state(l.getState()
                                        ).build());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        return response;
    }
}

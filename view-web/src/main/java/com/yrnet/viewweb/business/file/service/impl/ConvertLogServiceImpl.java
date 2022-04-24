package com.yrnet.viewweb.business.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.ConvertLogRequest;
import com.yrnet.viewweb.business.file.dto.ConvertLogResponse;
import com.yrnet.viewweb.business.file.dto.TransferRequest;
import com.yrnet.viewweb.business.file.dto.ConvertResponse;
import com.yrnet.viewweb.business.file.entity.ConvertLog;
import com.yrnet.viewweb.business.file.mapper.ConvertLogMapper;
import com.yrnet.viewweb.business.file.service.IConvertLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.file.service.TransferApi;
import com.yrnet.viewweb.business.login.entity.WxUser;
import com.yrnet.viewweb.business.login.service.WxUserService;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.http.TransferResponse;
import com.yrnet.viewweb.common.properties.ViewWebProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author dengbp
 */
@Service
public class ConvertLogServiceImpl extends ServiceImpl<ConvertLogMapper, ConvertLog> implements IConvertLogService {

    @Autowired
    private ViewWebProperties viewWebProperties;
    @Autowired
    private WxUserService wxUserServiceImpl;
    @Autowired
    private TransferApi transferApi;
    @Autowired
    private ConvertLogMapper convertLogMapper;
    private static  Integer FAIL = 2;



    @Override
    public List<ConvertLogResponse> queryLog(ConvertLogRequest dto) throws DocumentException {
        LambdaQueryWrapper<ConvertLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConvertLog::getUserOpenId,dto.getUserOpenId());
        queryWrapper.orderByDesc(ConvertLog::getCreateTime);
        List<ConvertLog> logs = this.list(queryWrapper);
        WxUser user = wxUserServiceImpl.queryByOpenId(dto.getUserOpenId());
        return this.returnFilList(logs,user);
    }

    @Override
    public ConvertLogResponse handle(FileConvertBo bo) throws DocumentException {
        try {
            bo.setFile(bo.getFiles()[0]);
            StringBuffer filePaths = new StringBuffer();
            for (MultipartFile f : bo.getFiles()){
                filePaths.append(storage(f)).append(",");
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
            return response;
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new DocumentException(e.getMessage());
        }
    }

    private String storage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String path = viewWebProperties.getFile_path()+ "/" + fileName;
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return path;
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

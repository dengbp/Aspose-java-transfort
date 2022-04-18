package com.yrnet.viewweb.business.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.ConvertLogRequest;
import com.yrnet.viewweb.business.file.dto.ConvertLogResponse;
import com.yrnet.viewweb.business.file.dto.TransferRequest;
import com.yrnet.viewweb.business.file.dto.TransferResponse;
import com.yrnet.viewweb.business.file.entity.ConvertLog;
import com.yrnet.viewweb.business.file.mapper.ConvertLogMapper;
import com.yrnet.viewweb.business.file.service.IConvertLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.file.service.TransferApi;
import com.yrnet.viewweb.business.login.entity.WxUser;
import com.yrnet.viewweb.business.login.service.WxUserService;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.properties.ViewWebProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
            ConvertLog log = bo.builderEntity();
            log.setFilePath(storage(bo.getFile()));
            convertLogMapper.insert(log);
            TransferResponse tr = transferApi.convert(TransferRequest.builder().fileId(log.getId()).filePath(log.getFilePath()).fileName(log.getFileName()).toType(log.getToType()).build());
            log.setNewFilePath(tr.getFilePath());
            log.setNewFileName(tr.getFileName());
            log.setNewFileSize(tr.getFileSize());
            log.setId(tr.getFileId());
            log.setState(tr.getState());
            this.updateById(log);
            ConvertLogResponse response = log.transformToRes();
            response.setUrl(viewWebProperties.getUrl_base()+ "/" +tr.getFileName());
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
        logs.forEach(l->
            response.add(ConvertLogResponse.builder()
                    .allow(0)
                    .createTime(l.getCreateTime())
                    .fileId(l.getId())
                    .url(viewWebProperties.getFile_path()+ "/" + l.getNewFileName())
                    .useName(user.getWxUserName())
                    .fileName(l.getFileName())
                    .fileSize(l.getFileSize())
                    .state(l.getState()
                    ).build())
        );
        return response;
    }
}

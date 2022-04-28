package com.yrnet.appweb.business.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.appweb.business.video.dto.ShareReqDto;
import com.yrnet.appweb.business.video.entity.ShareLog;
import com.yrnet.appweb.business.video.mapper.ShareLogMapper;
import com.yrnet.appweb.business.video.service.IShareDownloadAuthService;
import com.yrnet.appweb.business.video.service.IShareLogService;
import com.yrnet.appweb.common.exception.DocumentException;
import com.yrnet.appweb.common.service.ISeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dengbp
 */
@Service
public class ShareLogServiceImpl extends ServiceImpl<ShareLogMapper, ShareLog> implements IShareLogService {

    @Autowired
    private ISeqService seqService;
    @Resource
    private IShareDownloadAuthService shareDownloadAuthService;

    @Override
    public void add(ShareReqDto dto) throws DocumentException {
        ShareLog log = new ShareLog();
        this.transToEntity(dto,log);
        this.save(log);
        shareDownloadAuthService.insert(dto.getUserId(),dto.getShareTime());
    }

    private void transToEntity(ShareReqDto dto,ShareLog log){
        log.setId(seqService.getSeq());
        log.setCreateTime(Long.parseLong(dto.getShareTime()));
        log.setUserId(dto.getUserId());
        log.setUseTimes(0);
    }
}

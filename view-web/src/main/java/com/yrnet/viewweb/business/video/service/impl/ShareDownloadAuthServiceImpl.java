package com.yrnet.viewweb.business.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.video.entity.ShareDownloadAuth;
import com.yrnet.viewweb.business.video.mapper.ShareDownloadAuthMapper;
import com.yrnet.viewweb.business.video.service.IShareDownloadAuthService;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.properties.ViewWebProperties;
import com.yrnet.viewweb.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
@Slf4j
public class ShareDownloadAuthServiceImpl extends ServiceImpl<ShareDownloadAuthMapper, ShareDownloadAuth> implements IShareDownloadAuthService {

    @Autowired
    private ViewWebProperties yinXXProperties;


    @Override
    public void insert(String userId, String shareTime) throws DocumentException {
        Long st = Long.parseLong(shareTime.substring(0,8));
        LambdaQueryWrapper<ShareDownloadAuth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShareDownloadAuth::getUserId,userId);
        queryWrapper.eq(ShareDownloadAuth::getCreateTime,st);
        List<ShareDownloadAuth> list = this.list(queryWrapper);
        if (list.isEmpty()){
            ShareDownloadAuth auth = new ShareDownloadAuth();
            auth.setUserId(userId);
            auth.setUseTimes(0);
            auth.setCreateTime(st);
            this.save(auth);
            return;
        }
    }

    @Override
    public Integer getCurrentTimes(String userId) throws DocumentException {
        LambdaQueryWrapper<ShareDownloadAuth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShareDownloadAuth::getUserId,userId);
        queryWrapper.eq(ShareDownloadAuth::getCreateTime, Long.parseLong(DateUtil.current_yyyyMMdd()));
        List<ShareDownloadAuth> list = this.list(queryWrapper);
        if (list.isEmpty()){
            return yinXXProperties.getShare_download_limit();
        }
        return list.get(0).getUseTimes();
    }

    @Override
    public void updateTimes(String userId) throws DocumentException {
        LambdaQueryWrapper<ShareDownloadAuth> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShareDownloadAuth::getUserId,userId);
        List<ShareDownloadAuth> tmp = this.list(queryWrapper);
        if (tmp.isEmpty()){
            return;
        }
        ShareDownloadAuth auth = tmp.get(0);
        LambdaUpdateWrapper<ShareDownloadAuth> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(ShareDownloadAuth::getUseTimes,auth.getUseTimes()+1);
        updateWrapper.eq(ShareDownloadAuth::getUserId,userId);
        this.update(updateWrapper);
    }
}

package com.yrnet.viewweb.business.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.video.entity.ShareDownloadAuth;
import com.yrnet.viewweb.common.exception.YinXXException;

/**
 * @author dengbp
 */
public interface IShareDownloadAuthService extends IService<ShareDownloadAuth> {

    /**
     * Description todo
     * @param userId
 * @param shareTime
     * @return void
     * @throws YinXXException
     * @Author dengbp
     * @Date 12:53 AM 2/6/21
     **/

    void insert(String userId, String shareTime)throws YinXXException;

    /**
     * Description 获取当前下载次数
     * @param userId
     * @return java.lang.Integer
     * @throws YinXXException
     * @Author dengbp
     * @Date 12:34 AM 2/6/21
     **/
    Integer getCurrentTimes(String userId)throws YinXXException;

    /**
     * Description 更新下载次数
     * @param userId
     * @return void
     * @throws YinXXException
     * @Author dengbp
     * @Date 12:45 AM 2/6/21
     **/
    void updateTimes(String userId)throws YinXXException;
}

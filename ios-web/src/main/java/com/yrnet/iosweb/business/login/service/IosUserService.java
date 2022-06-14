package com.yrnet.iosweb.business.login.service;

import com.yrnet.iosweb.business.login.entity.IosUser;
import com.yrnet.iosweb.common.exception.DocumentException;

import java.util.Map;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:28 下午
 */
public interface IosUserService {

    /**
     * Description 用户登录校验
     * @param identityToken
 * @param user
     * @return boolean
     * @throws DocumentException
     * @Author dengbp
     * @Date 11:18 AM 6/10/22
     **/
    boolean verify(String identityToken,  IosUser user)throws DocumentException;



    IosUser queryByUserId(String userId);

}

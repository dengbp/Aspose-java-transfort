package com.yrnet.viewweb.business.bill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.bill.entity.UserAccount;
import com.yrnet.viewweb.common.exception.DocumentException;

/**
 * @author dengbp
 */
public interface IUserAccountService extends IService<UserAccount> {

    /**
     * Description todo
     * @param userId
     * @return com.yrnet.viewweb.business.bill.entity.UserAccount
     * @throws DocumentException
     * @Author dengbp
     * @Date 3:54 PM 6/20/21
     **/



    UserAccount getByUserId(String userId)throws DocumentException;

    /**
     * Description todo
     * @param userId 用户id
 * @param amount	本次充值金额，单位分
     * @return void
     * @throws DocumentException
     * @Author dengbp
     * @Date 4:05 PM 6/20/21
     **/


    void upInsert(String userId, Integer amount)throws DocumentException;

    /**
     * Description 更新下载次数
     * @param userId
     * @throws DocumentException
     * @return void
     * @Author dengbp
     * @Date 2:06 PM 6/27/21
     **/

    void updateByUserId(String userId)throws DocumentException;
}

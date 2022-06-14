package com.yrnet.pcweb.business.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.pcweb.business.bill.entity.UserAccount;
import com.yrnet.pcweb.business.bill.mapper.UserAccountMapper;
import com.yrnet.pcweb.business.bill.service.IUserAccountService;
import com.yrnet.pcweb.common.exception.DocumentException;
import com.yrnet.pcweb.common.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengbp
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {

    @Override
    public UserAccount getByUserId(String userId) throws DocumentException {
        List<UserAccount> userAccounts = list(new LambdaQueryWrapper<UserAccount>().eq(UserAccount::getStatus,0).eq(UserAccount::getUserId,userId));
        return (userAccounts == null || userAccounts.isEmpty())?null:userAccounts.get(0);
    }


    /** 套餐一(送10次) */
    static final int plan_1 = 1000;
    /** 套餐二(送50次) */
    static final int plan_2 = 3000;
    /** 套餐三(送100次) */
    static final int plan_3 = 5000;

    @Override
    public void upInsert(String userId, Integer amount) throws DocumentException {
        int base = 0;
        switch (amount) {
            case plan_1:
                base = 10;
                break;
            case plan_2:
                base = 50;
                break;
            case plan_3:
                base = 100;
                break;
            default:
        }
        UserAccount userAccount = this.getByUserId(userId);
        int add = amount/10 + base;
        if (userAccount != null){
            userAccount.setUpdateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
            userAccount.setAllTimes(userAccount.getAllTimes() + add);
            userAccount.setAbleTimes(userAccount.getAbleTimes() + add);
            updateById(userAccount);
            return;
        }
        userAccount = new UserAccount();
        userAccount.setUserId(userId);
        userAccount.setAllTimes(add);
        userAccount.setAbleTimes(userAccount.getAllTimes());
        userAccount.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        userAccount.setStatus(0);
        save(userAccount);
    }

    @Override
    public void updateByUserId(String userId) throws DocumentException {
        UserAccount userAccount = this.getByUserId(userId);
        if (userAccount != null){
            userAccount.setUpdateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
            userAccount.setAbleTimes(userAccount.getAbleTimes() - 1);
            updateById(userAccount);
            return;
        }
    }
}

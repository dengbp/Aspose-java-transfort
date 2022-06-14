package com.yrnet.iosweb.business.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.iosweb.business.login.entity.IosUser;
import com.yrnet.iosweb.business.login.mapper.IosUserMapper;
import com.yrnet.iosweb.business.login.service.IosUserService;
import com.yrnet.iosweb.common.exception.DocumentException;
import com.yrnet.iosweb.common.properties.ViewWebProperties;
import com.yrnet.iosweb.common.utils.AppleUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:29 下午
 */
@Service
public class IosUserServiceImpl extends ServiceImpl<IosUserMapper,IosUser> implements IosUserService {

    @Autowired
    private ViewWebProperties viewWebProperties;
    @Autowired
    private IosUserMapper iosUserMapper;


    @Override
    public boolean verify(String identityToken, IosUser user) throws DocumentException {
        Map<String, Object> result = new HashMap<>(20);
        boolean state = AppleUtil.verify(identityToken,result);
        if (state){
            BeanUtils.copyProperties(insert(result),user);
            user.setSession_key(identityToken);
        }
        return state;
    }


    private IosUser insert(Map<String, Object> result) {
        IosUser user = IosUser.builder(result,viewWebProperties.getAvatar_url());
        if (this.queryByUserId(user.getOpenId()) == null){
            iosUserMapper.insert(user);
        }
        return user;
    }

    @Override
    public IosUser queryByUserId(String userId) {
        return getOne(new LambdaQueryWrapper<IosUser>().eq(IosUser::getOpenId,userId));
    }
}

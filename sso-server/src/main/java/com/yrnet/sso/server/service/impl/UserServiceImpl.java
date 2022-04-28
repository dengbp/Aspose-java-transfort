package com.yrnet.sso.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.sso.server.core.model.UserInfo;
import com.yrnet.sso.server.dao.LoginLogMapper;
import com.yrnet.sso.server.dao.UserMapper;
import com.yrnet.sso.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService  {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private LoginLogMapper loginLogMapper;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(LoginLogMapper loginLogMapper,UserMapper userMapper){
        this.loginLogMapper = loginLogMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserInfo findUser(String username, String password) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(UserInfo::getPwd,MD5Encryption(password))
                .and(wrapper->wrapper.eq(UserInfo::getPhone,username)
                .or().eq(UserInfo::getUserCode,username).or().eq(UserInfo::getEmail,username));
        return this.getOne(queryWrapper);
    }

    @Override
    public void saveLoginLog(String userId,String ip) {
        this.loginLogMapper.saveLoginLog(userId,ip);
    }

    @Override
    public List<String> queryRoleIdByUserId(String userId) {
        return this.userMapper.queryRoleIdByUserId(userId);
    }


    private static String MD5Encryption(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }
}

package com.yrnet.viewweb.business.login.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yrnet.viewweb.business.login.config.WxConfig;
import com.yrnet.viewweb.business.login.entity.LoginDto;
import com.yrnet.viewweb.business.login.entity.LoginVo;
import com.yrnet.viewweb.business.login.entity.UserRelation;
import com.yrnet.viewweb.business.login.entity.WxUser;
import com.yrnet.viewweb.business.login.service.UserRelationService;
import com.yrnet.viewweb.business.login.service.WxUserService;
import com.yrnet.viewweb.common.entity.ViewWebResponse;
import com.yrnet.viewweb.common.utils.HttpClientUtil;
import com.yrnet.viewweb.common.utils.SeqUtil;
import com.yrnet.viewweb.common.utils.WeChatGetUserInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/11/29 2:21 下午
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    private final WxUserService wxUserService;
    private final UserRelationService userRelationService;
    @Autowired
    private WxConfig wxConfig;

    public LoginController(WxUserService wxUserService, UserRelationService userRelationService) {
        this.wxUserService = wxUserService;
        this.userRelationService = userRelationService;
    }


    @PostMapping("/login")
    public ViewWebResponse login(@RequestBody LoginDto loginDto) {
        ViewWebResponse response = new ViewWebResponse().success().message("success");
        validator(response,loginDto);
        if (!response.isNormal()){
            return response;
        }
        String apiUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxConfig.getAppId() + "&secret=" + wxConfig.getSecret() + "&js_code=" + loginDto.getCode() + "&grant_type=authorization_code";
        String responseBody = HttpClientUtil.doGet(apiUrl);
        JSONObject jsonObject = JSON.parseObject(responseBody);
        System.out.println(jsonObject.toJSONString());
        WxUser wxUser = new WxUser();
        UserRelation userRelation = new UserRelation();
        if (StringUtils.isNotBlank(jsonObject.getString("openid"))
                && StringUtils.isNotBlank(jsonObject.getString("session_key"))) {
            JSONObject userInfoJson= WeChatGetUserInfoUtil.getUserInfo(loginDto.getEncryptedData(),jsonObject.getString("session_key"),loginDto.getIv());
            if (userInfoJson == null){
                return response.fail().message("解析用户信息失败");
            }
            wxUser.getWxUser(userInfoJson);
            WxUser wxUserFromData = wxUserService.queryByOpenId(wxUser.getWxOpenId());
            if (wxUserFromData == null) {
                wxUser.setWxUserId(SeqUtil.nextId());
                wxUser.setStatus(1);
                wxUser.setAuthorityId(1);
                wxUser.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                wxUserService.insert(wxUser);
                userRelation.setUserId(SeqUtil.nextId());
                userRelation.setType(1);
                userRelation.setWxOpenId(wxUser.getWxOpenId());
                userRelationService.insert(userRelation);
            } else {
                wxUser = wxUserService.queryByOpenId(wxUser.getWxOpenId());
                userRelation = userRelationService.selectByWxOpenId(wxUser.getWxOpenId(), 1);
            }
        }
        return response.data(LoginVo.builder().wxUser(wxUser).sessionKey(jsonObject.getString("session_key")).userRelation(userRelation).build());
    }

    private void validator(ViewWebResponse  response,LoginDto loginDto){
        if (StringUtils.isBlank(loginDto.getEncryptedData())){
            response.fail().message("encryptedData is null");
            return;
        }
        if (StringUtils.isBlank(loginDto.getIv())){
            response.fail().message("iv is null");
            return;
        }
        if (StringUtils.isBlank(loginDto.getCode())){
            response.fail().message("code is null");
            return;
        }

    }

    @PostMapping("/phone")
    public ViewWebResponse getPhone(@RequestBody LoginDto loginDto){
        JSONObject userInfoJson= WeChatGetUserInfoUtil.getUserInfo(loginDto.getEncryptedData(),loginDto.getSessionKey(),loginDto.getIv());
        assert userInfoJson != null;
        WxUser wxUserFromData = wxUserService.queryByOpenId(loginDto.getWxOpenId());
        System.out.println(userInfoJson.toJSONString());
        wxUserFromData.setPhoneNumber(userInfoJson.getString("purePhoneNumber"));
        wxUserService.updateByWxUserId(wxUserFromData);
        return new ViewWebResponse().success().data(wxUserFromData);
    }

}

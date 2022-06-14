package com.yrnet.iosweb.business.login.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author baiyang
 * @version 1.0
 * @date 2020/12/1 7:29 下午
 */
@Data
@Builder
public class LoginVo {
    private IosUser iosUser;
}

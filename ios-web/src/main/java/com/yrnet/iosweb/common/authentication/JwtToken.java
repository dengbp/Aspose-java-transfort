package com.yrnet.iosweb.common.authentication;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * All rights Reserved, Designed By SEGI
 * <pre>
 * Copyright:  Copyright(C) 2018
 * Company:    SEGI.
 * @Author:     dengbp
 * @Date: 2018/7/27
 * </pre>
 * <p>
 *    jwt token信息
 * </p>
 */
public class JwtToken implements AuthenticationToken {

    public static String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAPZ6OZ8P4ctuxZRnC1SpFzZvhYkXl+DSu/EFCnlxBCCOEZE12k82Dh0Ft6c5eDAN0NonAb4X2g4Sv+mAT/L3Z6ghYfW3BSW+MtUXeaQM8BZ31O0xDfDqHuT8u3zclkornFUcmN8zlI9fdfmuUqa9ozrcNL5zLSvp6tanuhkVmJrNAgMBAAECgYEAkKLk0VyGidR1CUL2uTWIs57545r+uyWuuB7XKLCTVW9JC8doVndIDOvf+7eTuc9BseKV+TER+pQyHhn30IIyRqaDPzz7l/k9FmvA/b6S8omfA+q1amRYcXkOs0gcfqh+h5VZnudZZ3NfIRljgok/ZrkrdiR+DsTd7zx23uCjqwECQQD/7cm5+y1jn/ZACklqaFxW6kWTsQLmsY65/E2OYjSCEs1GZnh+xURGuvoWnnYbh8tZQodhMJuL3PAuJr3OrpCtAkEA9ovDt7zyXuFQc3Th4wc1BOiYIh0V41mYOyg5T8ahqFTFEKWxKSN+gdI6EvrzRH0udZQU9ksyXrBLWLfFyTvWoQJAbHPb1FA4f8s9kw3I5qqq1roYGeodDJ2Zmf7s7rV3Atfs3TR3Q2Fc+YwqhoNIEYqOBZlHvUvX3oEV1rjcHkWjUQJAXvdfIDsrsNhhQNfY2rY/+fAOCbXiZHxyYvcPfGcqhciBT/RC1XHSnPb5X65CXjNJ3gh4OpakhEyr+TRwfx8JgQJAU7xuBNqpJBSpdtSmnDEu7l2RbtDU0lhnteWs5yFKvbMJrX9WdLWXOWRYo/rKUOYvZyO8JiS/Tc5nOlRd6R+ytQ==";
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2ejmfD+HLbsWUZwtUqRc2b4WJF5fg0rvxBQp5cQQgjhGRNdpPNg4dBbenOXgwDdDaJwG+F9oOEr/pgE/y92eoIWH1twUlvjLVF3mkDPAWd9TtMQ3w6h7k/Lt83JZKK5xVHJjfM5SPX3X5rlKmvaM63DS+cy0r6erWp7oZFZiazQIDAQAB";

    /**
     * 密钥
     */
    private String token;
    private String exipreAt;


    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken(String token, String exipreAt) {
        this.token = token;
        this.exipreAt = exipreAt;
    }

    public String getToken() {
        return token;
    }

    public String getExipreAt() {
        return exipreAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

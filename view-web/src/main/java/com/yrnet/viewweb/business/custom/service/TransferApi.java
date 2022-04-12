package com.yrnet.viewweb.business.custom.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dengbp
 * @ClassName TransferApi
 * @Description TODO
 * @date 4/9/22 10:50 PM
 */
@FeignClient(name = "services-transfer")
public interface TransferApi {

    @RequestMapping(value = "sample/hello")
    String hello();
}

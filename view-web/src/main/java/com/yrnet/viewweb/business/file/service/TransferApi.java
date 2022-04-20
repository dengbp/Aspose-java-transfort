package com.yrnet.viewweb.business.file.service;

import com.yrnet.viewweb.business.file.dto.TransferRequest;
import com.yrnet.viewweb.business.file.dto.ConvertResponse;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.http.TransferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dengbp
 * @ClassName TransferApi
 * @Description TODO
 * @date 4/9/22 10:50 PM
 */
@FeignClient(name = "services-transfer")
public interface TransferApi {

    @RequestMapping(value = "services-transfer/sample/hello")
    String hello();

    /**
     * Description todo
     * @param request
     * * word转pdf:1
     *      * pdf转word:2
     *      * excel转pdf:3
     *      * pdf转excel:4
     *      * ppt转pdf:5
     *      * pdf转ppt:6
     *      * jpg转pdf:7
     *      * pdf转jpg:8
     *      * png转pdf:9
     *      * pdf转png:10
     *      * docx转pdf:11
     *      * pdf转docx:12
     *      * odt转pdf:13
     *      * doc转pdf:14
     * @return com.yrnet.viewweb.business.file.dto.ConvertLogResponse
     * @Author dengbp
     * @Date 4:03 PM 4/18/22
     **/

    @PostMapping(value = "services-transfer/format-convert/exe")
    TransferResponse convert(TransferRequest request)throws DocumentException;


}

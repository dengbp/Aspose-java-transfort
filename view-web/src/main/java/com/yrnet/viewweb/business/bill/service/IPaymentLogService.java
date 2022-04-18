package com.yrnet.viewweb.business.bill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.bill.dto.PayMentReqDto;
import com.yrnet.viewweb.business.bill.dto.PaymentLogReqDto;
import com.yrnet.viewweb.business.bill.dto.PaymentLogRespDto;
import com.yrnet.viewweb.business.bill.entity.PaymentLog;
import com.yrnet.viewweb.common.exception.DocumentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author dengbp
 */
public interface IPaymentLogService extends IService<PaymentLog> {
    Map<String, Object> wxPayment(PayMentReqDto reqDto) throws Exception;

    String wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * Description todo
     * @param reqDto
     * @return java.util.List<com.yrnet.viewweb.business.bill.dto.PaymentLogRespDto>
     * @throws DocumentException
     * @Author dengbp
     * @Date 4:29 PM 2/5/21
     **/
    List<PaymentLogRespDto> queryPayLog(PaymentLogReqDto reqDto) throws DocumentException;
}

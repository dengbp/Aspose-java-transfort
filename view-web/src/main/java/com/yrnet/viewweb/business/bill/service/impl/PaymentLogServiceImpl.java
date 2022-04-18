package com.yrnet.viewweb.business.bill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.bill.dto.PayMentReqDto;
import com.yrnet.viewweb.business.bill.dto.PaymentLogReqDto;
import com.yrnet.viewweb.business.bill.dto.PaymentLogRespDto;
import com.yrnet.viewweb.business.bill.entity.OrderPay;
import com.yrnet.viewweb.business.bill.entity.PaymentLog;
import com.yrnet.viewweb.business.bill.entity.PlanService;
import com.yrnet.viewweb.business.bill.mapper.PaymentLogMapper;
import com.yrnet.viewweb.business.bill.service.IPaymentLogService;
import com.yrnet.viewweb.business.bill.service.IPlanServiceService;
import com.yrnet.viewweb.business.bill.service.IUserAccountService;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.service.ISeqService;
import com.yrnet.viewweb.common.utils.DateUtil;
import com.yrnet.viewweb.common.utils.PayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dengbp
 */
@Service
@Slf4j
public class PaymentLogServiceImpl extends ServiceImpl<PaymentLogMapper, PaymentLog> implements IPaymentLogService {

    @Resource
    private IPlanServiceService planServiceService;

    @Resource
    private IUserAccountService userAccountService;

    @Autowired
    private ISeqService seqService;

    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final Random RANDOM = new SecureRandom();

    @Value("${pays.appid}")
    public String appid;

    @Value("${pays.mchid}")
    public String mchid;

    @Value("${pays.key}")
    public String keyValue;

    @Value("${pays.notifyurl}")
    public String notifyUrl;

    @Override
    public Map<String, Object> wxPayment(PayMentReqDto reqDto) throws Exception {
        /**
         * 随机字符串
         */
        String nonce_str = generateNonceStr();

        /**
         * 订单编号
         */
        String out_trade_no = "";

        /**
         * 订单总金额，单位为分（这个注意必须String类型）
         */
        DecimalFormat df1 = new DecimalFormat("0");
        String total_fee = df1.format(Double.valueOf(reqDto.getTotal_fee()) * 100);

        /**
         * 获取当前ip
         */
        String spbill_create_ip = getShowIpv4();
        String body = "去印服务";

        /**
         * 小程序id
         */
        String AppId = appid;

        /**
         * 商户id
         */
        String MchId = mchid;

        /**
         * 通知地址(微信官方上找的实例能用就行)
         */
        String NotifyUrl = notifyUrl;

        /**
         * 商户密钥
         */
        String key = keyValue;

        /**
         * 构造成类
         */
        OrderPay orderPay = new OrderPay(AppId, MchId, nonce_str, body, out_trade_no, total_fee, spbill_create_ip, NotifyUrl, "JSAPI", reqDto.getOpenId(), key, "https://api.mch.weixin.qq.com/pay/unifiedorder");

        /**
         * 保存支付单到本地数据库中
         */
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setId(seqService.getSeq());
        paymentLog.setUserId(reqDto.getOpenId());
        paymentLog.setProducerId(reqDto.getProducerId());
        paymentLog.setProducerName(reqDto.getProducerName());

        BigDecimal amount = new BigDecimal(total_fee);
        paymentLog.setAmount(amount);

        /**
         * 0待支付，1失败，2成功
         */
        paymentLog.setStatus(0);
        paymentLog.setRemark(body);
        paymentLog.setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
        boolean savePayData = this.save(paymentLog);
        if (savePayData) {
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            String showMonth = month > 10 ? "" + month : "0" + month;
            int year = cal.get(Calendar.YEAR);

            orderPay.setOut_trade_no(year + "" + showMonth + "" + paymentLog.getId());
            /**
             * 调用方法，得到统一下单地数据
             */
            Map<String, Object> pac = PayUtil.wxPay(orderPay);

            /**
             * 直接返回给前端，前端调用即可
             */
            return pac;
        } else {
            return null;
        }
    }

    @Override
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String resXml = "";

        Map notityMap = PayUtil.wxNotify(request, response, "");
        if(notityMap == null || notityMap.get("return_code") == null){
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return resXml;
        }

        String returnCode = (String) notityMap.get("return_code");
        log.info("wxNotify=>returnCode=>" + returnCode);
        if ("SUCCESS".equals(returnCode)) {
            resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg></xml> ";

            String order_no = (String) notityMap.get("out_trade_no");
            log.info("wxNotify=>order_no=>" + order_no);
            PaymentLog paymentLog = this.getById(Integer.valueOf(order_no.substring(6)));
            /**
             * 付款成功
             */
            paymentLog.setStatus(2);
            this.updateById(paymentLog);
            userAccountService.upInsert(paymentLog.getUserId(),paymentLog.getAmount().intValue());
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        return resXml;
    }

    @Override
    public List<PaymentLogRespDto> queryPayLog(PaymentLogReqDto reqDto) throws DocumentException {
        LambdaQueryWrapper<PaymentLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PaymentLog::getStatus,2).eq(PaymentLog::getUserId,reqDto.getUserId()).orderByDesc(PaymentLog::getCreateTime);
        List<PaymentLog> logs = this.list(queryWrapper);
        List<PaymentLogRespDto> respDtos = new ArrayList<>();
        if (logs.isEmpty()){
            return respDtos;
        }
        this.transToResp(logs,respDtos);
        return respDtos;
    }

    private void transToResp(List<PaymentLog> sources,List<PaymentLogRespDto> response){
        sources.forEach(e->{
            PlanService planService = planServiceService.getById(e.getProducerId());
            if(planService != null){
                PaymentLogRespDto respDto = new PaymentLogRespDto();
                respDto.setCreateTime(e.getCreateTime());
                respDto.setEffectiveTime(planService.getEffectiveTime());
                respDto.setProducerName(e.getProducerName());
                respDto.setAmount(e.getAmount().intValue());
                response.add(respDto);
            }

        });
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    private static String generateNonceStr() {
        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }
        return new String(nonceChars);
    }


    /**
     * 商户订单号
     *
     * @return
     */
    private static String getStringToday() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);

        char[] nonceChars = new char[10];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
        }

        return dateString + new String(nonceChars);
    }

    /**
     * 获取当前ip
     *
     * @return
     */
    private static String getShowIpv4() {
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            return ip4.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "ip地址错误";
    }
}

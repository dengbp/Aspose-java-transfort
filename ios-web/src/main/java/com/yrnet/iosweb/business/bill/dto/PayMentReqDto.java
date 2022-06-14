package com.yrnet.iosweb.business.bill.dto;

import com.yrnet.iosweb.common.entity.Token;
import lombok.Data;

/**
 * @author dengbp
 * @ClassName PayMentReqDto
 * @Description TODO
 * @date 2/2/21 3:13 PM
 */
@Data
public class PayMentReqDto extends Token{
    private static final long serialVersionUID = 1L;
    private String appid;//正式小程序的appid
    private String mch_id;//商户号的id
    private String nonce_str;//随机字符串
    private String body;//商品主题内容
    private String out_trade_no;//交易号
    private String total_fee;//总的费用
    private String spbill_create_ip;//本地ip，谁调用就是谁的ip
    private String notify_url;//微信回调地址
    private String trade_type="JSAPI";//类型
    private String openId;//用户openid
    private String key;//商户号在平台上设置的密匙，自己设置的
    private String pay_url;//微信支付的地址
    private Integer producerId; // 商品ID
    private String producerName; // 商品名称

    public PayMentReqDto(){

    }

    public PayMentReqDto(String appid, String mch_id, String nonce_str, String body, String out_trade_no, String total_fee, String spbill_create_ip, String notify_url, String trade_type, String openId, String key, String pay_url, Integer producerId, String producerName) {
        this.appid = appid;
        this.mch_id = mch_id;
        this.nonce_str = nonce_str;
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.notify_url = notify_url;
        this.trade_type = trade_type;
        this.openId = openId;
        this.key = key;
        this.pay_url = pay_url;
        this.producerId = producerId;
        this.producerName = producerName;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPay_url() {
        return pay_url;
    }

    public void setPay_url(String pay_url) {
        this.pay_url = pay_url;
    }

    public Integer getProducerId() {
        return producerId;
    }

    public void setProducerId(Integer producerId) {
        this.producerId = producerId;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }
}

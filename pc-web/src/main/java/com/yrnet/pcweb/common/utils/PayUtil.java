package com.yrnet.pcweb.common.utils;

import com.yrnet.pcweb.business.bill.entity.OrderPay;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Slf4j
public class PayUtil {
    /**
     * 调用支付 统一下单 接口，返回Map数据
     *
     * @param orderPay
     * @return
     */
    public static Map<String, Object> wxPay(OrderPay orderPay) {
        try {
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", orderPay.getAppid());
            packageParams.put("mch_id", orderPay.getMch_id());
            packageParams.put("nonce_str", orderPay.getNonce_str());
            packageParams.put("body", orderPay.getBody());
            packageParams.put("out_trade_no", orderPay.getOut_trade_no());
            packageParams.put("total_fee", orderPay.getTotal_fee());
            packageParams.put("spbill_create_ip", orderPay.getSpbill_create_ip());
            packageParams.put("notify_url", orderPay.getNotify_url());
            packageParams.put("trade_type", orderPay.getTrade_type());
            packageParams.put("openid", orderPay.getOpenid());

            /**
             * 除去数组中的空值和签名参数
             */
            packageParams = PayUtil.paraFilter(packageParams);

            /**
             * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
             */
            String prestr = PayUtil.createLinkString(packageParams);

            /**
             * MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
             */
            String mysign = PayUtil.sign(prestr, orderPay.getKey(), "utf-8").toUpperCase();

            /**
             * 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
             */
            String xml = "<xml version='1.0' encoding='utf-8'>" + "<appid>" + orderPay.getAppid() + "</appid>"
                    + "<body><![CDATA[" + orderPay.getBody() + "]]></body>"
                    + "<mch_id>" + orderPay.getMch_id() + "</mch_id>"
                    + "<nonce_str>" + orderPay.getNonce_str() + "</nonce_str>"
                    + "<notify_url>" + orderPay.getNotify_url() + "</notify_url>"
                    + "<openid>" + orderPay.getOpenid() + "</openid>"
                    + "<out_trade_no>" + orderPay.getOut_trade_no() + "</out_trade_no>"
                    + "<spbill_create_ip>" + orderPay.getSpbill_create_ip() + "</spbill_create_ip>"
                    + "<total_fee>" + orderPay.getTotal_fee() + "</total_fee>"
                    + "<trade_type>" + orderPay.getTrade_type() + "</trade_type>"
                    + "<sign>" + mysign + "</sign>"
                    + "</xml>";

            /**
             * 调用统一下单接口，并接受返回的结果
             */
            String result = PayUtil.httpRequest(orderPay.getPay_url(), "POST", xml);

            /**
             * 将解析结果存储在HashMap中
             */
            Map map = PayUtil.doXMLParse(result);

            /**
             * 返回状态码
             */
            String return_code = (String) map.get("return_code");

            /**
             * 返回给移动端需要的参数
             */
            Map<String, Object> response = new HashMap<String, Object>();
            if (return_code.equals("SUCCESS")) {
                /**
                 * 返回的预付单信息
                 */
                String prepay_id = (String) map.get("prepay_id");
                response.put("nonceStr", orderPay.getNonce_str());
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;

                /**
                 * 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                 */
                response.put("timeStamp", timeStamp + "");

                String stringSignTemp = "appId=" + orderPay.getAppid() + "&nonceStr=" + orderPay.getNonce_str() + "&package=prepay_id=" + prepay_id + "&signType=" + "MD5" + "&timeStamp=" + timeStamp;

                /**
                 * 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                 */
                String paySign = PayUtil.sign(stringSignTemp, orderPay.getKey(), "utf-8").toUpperCase();
                response.put("paySign", paySign);

                response.put("appid", orderPay.getOpenid());
                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sign(String text, String key, String input_charset) {
        text = text + "&key=" + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    public static boolean verify(String text, String sign, String key, String input_charset) {
        text = text + key;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    public static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 生成6位或10位随机数 param codeLength(多少位)
     *
     * @return
     */
    public static String createCode(int codeLength) {
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            code += (int) (Math.random() * 9);
        }
        return code;
    }

    private static boolean isValidChar(char ch) {
        if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
            return true;
        }

        /**
         * 简体中文汉字编码
         */
        if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f)) {
            return true;
        }
        return false;
    }

    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }
        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            /**
             * 拼接时，不包括最后一个&字符
             */
            if (i == keys.size() - 1) {
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuffer buffer = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            /**
             * 往服务器端写内容
             */
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            /**
             * 读取服务器端返回的内容
             */
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map doXMLParse(String strxml) throws Exception {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream in = String2Inputstream(strxml);
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List list = root.getChildren();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Element e = (Element) it.next();
            String k = e.getName();
            String v = "";
            List children = e.getChildren();
            if (children.isEmpty()) {
                v = e.getTextNormalize();
            } else {
                v = getChildrenText(children);
            }
            m.put(k, v);
        }

        //关闭流
        in.close();
        return m;
    }

    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }
        return sb.toString();
    }

    public static InputStream String2Inputstream(String str) throws UnsupportedEncodingException {
        return new ByteArrayInputStream(str.getBytes("utf-8"));
    }

    /**
     * 支付成功后调用方法中的执行函数，执行后，得到微信推送的消息，返回map
     *
     * @param request
     * @param response
     * @param key
     * @return
     * @throws Exception
     */
    public static Map wxNotify(HttpServletRequest request, HttpServletResponse response, String key) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        String notityXml = sb.toString();
        // String notityXml = "<xml><appid><![CDATA[wx51e10e9977f1c819]]></appid><bank_type><![CDATA[OTHERS]]></bank_type><cash_fee><![CDATA[20]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1503084061]]></mch_id><nonce_str><![CDATA[kYcGGBVXADweFdHHd6mUMsCdxaM6yol5]]></nonce_str><openid><![CDATA[oNEyZ5R-HrhthIVisOivNps3K830]]></openid><out_trade_no><![CDATA[202102321]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[C0A2224B939034DB08516CF0FD893969]]></sign><time_end><![CDATA[20210205104946]]></time_end><total_fee>20</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000796202102055841422834]]></transaction_id></xml>";
        log.info("接收到的报文=>" + notityXml);

        Map map = PayUtil.doXMLParse(notityXml);
        return map;
//        String returnCode = (String) map.get("return_code");
//        if ("SUCCESS".equals(returnCode)) {
//            resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
//                    + "<return_msg><![CDATA[OK]]></return_msg></xml> ";
//
//            //验证签名是否正确
////            if (PayUtil.verify(PayUtil.createLinkString(map), (String) map.get("sign"), key, "utf-8")) {
////                /**此处添加自己的业务逻辑代码start**/
////                /**此处添加自己的业务逻辑代码end**/
////                //通知微信服务器已经支付成功
////                resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>"
////                        + "<return_msg><![CDATA[OK]]></return_msg></xml> ";
////            }
//        } else {
//            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
//        }
//        log.info(resXml);
        // log.info("微信支付回调数据结束");


//        BufferedOutputStream out = new BufferedOutputStream(
//                response.getOutputStream());
//        out.write(resXml.getBytes());
//        out.flush();
//        out.close();
    }
}

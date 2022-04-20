package com.yrent.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class BitchUtil {
    public static String createBatchNo() {
        // 批次号第一部分：时间
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTimeStr = dateFormat.format(new Date());
        // 批次号第二部分：随机数
        Random random = new Random();
        Integer cusCode = random.nextInt(900000) + 100000;
        String cusCodeStr = cusCode.toString();
        // 返回分配批次
        return currentTimeStr + cusCodeStr;
    }
}

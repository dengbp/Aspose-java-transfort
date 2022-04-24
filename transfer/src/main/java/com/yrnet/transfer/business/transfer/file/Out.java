package com.yrnet.transfer.business.transfer.file;

import lombok.extern.slf4j.Slf4j;


/**
 * Description todo
 * @Author dengbp
 * @Date 6:47 PM 3/11/22
 **/
@Slf4j
public class Out {

    public static void print(String inPath,String outPath,long now,long old){
       double time = (now - old) / 1000.0;
        log.info("源文件："+inPath+",N");
        log.info("现文件："+outPath);
        log.info("转换成功,共耗时：" + time+ "秒");
    }
}

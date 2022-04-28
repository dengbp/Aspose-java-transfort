package com.yrnet.appweb.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.common.mapper.Seq;

/**
 * @author dengbp
 */
public interface ISeqService extends IService<Seq> {

    /**
     * Description todo
     * @param
     * @return long
     * @Author dengbp
     * @Date 14:55 2020-05-26
     **/
    long getSeq();

}
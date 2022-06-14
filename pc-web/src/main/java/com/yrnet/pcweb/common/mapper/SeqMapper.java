package com.yrnet.pcweb.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author dengbp
 */
public interface SeqMapper extends BaseMapper<Seq> {

    /**
     * Description todo
     * @param seq
     * @return long
     * @Author dengbp
     * @Date 14:54 2020-05-26
     **/

    long insertSeq(Seq seq);
}

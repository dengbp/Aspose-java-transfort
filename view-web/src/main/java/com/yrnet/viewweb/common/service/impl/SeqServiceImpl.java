package com.yrnet.viewweb.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.common.mapper.Seq;
import com.yrnet.viewweb.common.mapper.SeqMapper;
import com.yrnet.viewweb.common.service.ISeqService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author dengbp
 */
@Service
public class SeqServiceImpl extends ServiceImpl<SeqMapper, Seq> implements ISeqService {

    @Resource
    SeqMapper seqMapper;
    @Override
    public long getSeq() {
        Seq seq = new Seq();
        seq.setCreateTime(LocalDate.now());
        seq.setSeq(0L);
        seqMapper.insertSeq(seq);
        return seq.getId();
    }
}

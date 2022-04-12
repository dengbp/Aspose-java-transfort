package com.yrnet.viewweb.common.sequence.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.common.entity.LicenseTaskSeq;
import com.yrnet.viewweb.common.mapper.LicenseTaskSeqMapper;
import com.yrnet.viewweb.common.sequence.LicenseTaskSeqService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class LicenseTaskSeqServiceImpl  extends ServiceImpl<LicenseTaskSeqMapper, LicenseTaskSeq> implements LicenseTaskSeqService {

    @Resource
    LicenseTaskSeqMapper taskSeqMapper;
    @Override
    public long getSeq() {
        LicenseTaskSeq seq = new LicenseTaskSeq();
        seq.setCreateTime(LocalDate.now());
        seq.setSeq(0L);
        taskSeqMapper.insertSeq(seq);
        return seq.getId();
    }
}

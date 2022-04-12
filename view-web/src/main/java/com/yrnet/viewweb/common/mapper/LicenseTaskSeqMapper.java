package com.yrnet.viewweb.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.viewweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqMapper extends BaseMapper<LicenseTaskSeq> {
    long insertSeq(LicenseTaskSeq seq);
}

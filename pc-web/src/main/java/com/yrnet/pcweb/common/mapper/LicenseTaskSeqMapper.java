package com.yrnet.pcweb.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.pcweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqMapper extends BaseMapper<LicenseTaskSeq> {
    long insertSeq(LicenseTaskSeq seq);
}

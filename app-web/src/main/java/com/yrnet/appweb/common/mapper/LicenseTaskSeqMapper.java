package com.yrnet.appweb.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.appweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqMapper extends BaseMapper<LicenseTaskSeq> {
    long insertSeq(LicenseTaskSeq seq);
}

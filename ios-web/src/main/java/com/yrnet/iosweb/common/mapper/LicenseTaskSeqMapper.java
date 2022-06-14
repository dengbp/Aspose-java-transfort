package com.yrnet.iosweb.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.iosweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqMapper extends BaseMapper<LicenseTaskSeq> {
    long insertSeq(LicenseTaskSeq seq);
}

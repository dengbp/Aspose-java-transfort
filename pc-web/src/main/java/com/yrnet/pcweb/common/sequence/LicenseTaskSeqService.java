package com.yrnet.pcweb.common.sequence;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.pcweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqService extends IService<LicenseTaskSeq> {
    long getSeq();
}

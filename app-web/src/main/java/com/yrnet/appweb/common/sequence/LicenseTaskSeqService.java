package com.yrnet.appweb.common.sequence;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqService extends IService<LicenseTaskSeq> {
    long getSeq();
}

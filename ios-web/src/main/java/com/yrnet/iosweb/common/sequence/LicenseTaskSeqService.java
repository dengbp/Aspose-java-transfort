package com.yrnet.iosweb.common.sequence;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.iosweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqService extends IService<LicenseTaskSeq> {
    long getSeq();
}

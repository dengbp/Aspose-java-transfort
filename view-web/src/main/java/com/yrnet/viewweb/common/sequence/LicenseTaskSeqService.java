package com.yrnet.viewweb.common.sequence;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.common.entity.LicenseTaskSeq;

public interface LicenseTaskSeqService extends IService<LicenseTaskSeq> {
    long getSeq();
}

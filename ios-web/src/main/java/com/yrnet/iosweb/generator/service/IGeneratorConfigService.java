package com.yrnet.iosweb.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.iosweb.generator.entity.GeneratorConfig;

/**
 * @author dengbp
 */
public interface IGeneratorConfigService extends IService<GeneratorConfig> {

    /**
     * 查询
     *
     * @return GeneratorConfig
     */
    GeneratorConfig findGeneratorConfig();

    /**
     * 修改
     *
     * @param generatorConfig generatorConfig
     */
    void updateGeneratorConfig(GeneratorConfig generatorConfig);

}
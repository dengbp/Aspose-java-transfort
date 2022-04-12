package com.yrnet.viewweb.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.common.exception.YinXXException;
import com.yrnet.viewweb.system.Dto.SystemParamsDto;
import com.yrnet.viewweb.system.entity.SystemParams;
import com.yrnet.viewweb.system.mapper.SystemParamsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SystemParamsService extends ServiceImpl<SystemParamsMapper, SystemParams> {

    public boolean create(SystemParams sysParams) {
        return this.save(sysParams);
    }

    public boolean update(SystemParams sysParams) {
        return this.updateById(sysParams);
    }


    public boolean delete(Long id) {
        LambdaQueryWrapper<SystemParams> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemParams::getId,id).or(wrapper1->wrapper1.eq(SystemParams::getParentId,id));
        return this.remove(wrapper);
    }

    public IPage<SystemParams> getList(SystemParamsDto request) {
        Page<SystemParams> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<SystemParams> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getParamName())){
            queryWrapper.eq(SystemParams::getParamName,request.getParamName());
        }
        if (request.getParamType() != null){
            queryWrapper.eq(SystemParams::getParamType,request.getParamType());
        }
        queryWrapper.orderByDesc(SystemParams::getCreateTime);
        return this.baseMapper.selectPage(page,queryWrapper);
    }

    public List<SystemParams> getByParentId(SystemParamsDto request)throws YinXXException {
        LambdaQueryWrapper<SystemParams> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getParamName())){
            queryWrapper.eq(SystemParams::getParamName,request.getParamName());
        }
        if (request.getParentId() !=  null){
            queryWrapper.eq(SystemParams::getParentId,request.getParentId());
        }
        if (request.getParamType() != null){
            queryWrapper.eq(SystemParams::getParamType,request.getParamType());
        }

        return this.baseMapper.selectList(queryWrapper);
    }

    public List<SystemParams> findByParamCode(String[]paramCodes){
        List<String> codes = Stream.of(paramCodes).collect(Collectors.toList());
        LambdaQueryWrapper<SystemParams> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SystemParams::getParamCode,codes);
        return this.list(wrapper);
    }

}

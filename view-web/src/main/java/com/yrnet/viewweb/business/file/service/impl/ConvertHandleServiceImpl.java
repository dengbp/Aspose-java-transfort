package com.yrnet.viewweb.business.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.viewweb.business.bill.entity.UserAccount;
import com.yrnet.viewweb.business.bill.service.IUserAccountService;
import com.yrnet.viewweb.business.file.bo.FileConvertBo;
import com.yrnet.viewweb.business.file.dto.ConvertLogRequest;
import com.yrnet.viewweb.business.file.dto.ConvertLogResponse;
import com.yrnet.viewweb.business.file.entity.ParseLog;
import com.yrnet.viewweb.business.file.mapper.ParseLogMapper;
import com.yrnet.viewweb.business.file.service.IConvertHandleService;
import com.yrnet.viewweb.business.login.service.WxUserService;
import com.yrnet.viewweb.common.exception.DocumentException;
import com.yrnet.viewweb.common.properties.ViewWebProperties;
import com.yrnet.viewweb.common.service.ISeqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dengbp
 */
@Service
@Slf4j
public class ConvertHandleServiceImpl extends ServiceImpl<ParseLogMapper, ParseLog> implements IConvertHandleService {

    @Autowired
    private ViewWebProperties viewWebProperties;
    @Autowired
    private WxUserService  wxUserServiceImpl;
    @Autowired
    private ParseLogMapper parseLogMapper;
    @Autowired
    private ISeqService seqService;
    @Resource
    private IUserAccountService userAccountService;


    @Override
    public List<ConvertLogResponse> queryLog(ConvertLogRequest dto) throws DocumentException {
        LambdaQueryWrapper<ParseLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParseLog::getUserId,dto.getUserOpenId());
        queryWrapper.orderByDesc(ParseLog::getCreateTime);
        List<ParseLog> logs = this.list(queryWrapper);
        UserAccount userAccount = userAccountService.getByUserId(dto.getUserOpenId());
        Integer allow = 1;
        if (userAccount != null && userAccount.getAbleTimes()>0){
            allow = 2;
        }
        return this.returnVideoList(logs,allow);
    }

    @Override
    public ConvertLogResponse convert(FileConvertBo bo) throws DocumentException {

        return ConvertLogResponse.builder().build();
    }

    private List<ConvertLogResponse> returnVideoList(List<ParseLog> logs, Integer allow ){
        List<ConvertLogResponse> respDtos = new ArrayList<>();
        if(logs == null){
            return respDtos;
        }
        this.transToResp(logs,respDtos);
        return respDtos;
    }


    private void transToResp(List<ParseLog> sources, List<ConvertLogResponse> response ){
        Map<String,Byte> tmp = new HashMap<>();
        sources.stream().forEach(e->{
            if (tmp.containsKey(e.getParseUrl())){
                return;
            }
            tmp.put(e.getParseUrl(),Byte.parseByte("0"));
            ConvertLogResponse respDto = ConvertLogResponse.builder().build();
            respDto.setCreateTime(e.getCreateTime());
            respDto.setState(0);
            respDto.setUrl(e.getActiveUrl());
            JSONObject root = JSONObject.parseObject(e.getParseResult());
            if (root == null){
                respDto.setState(1);
            }else {
                JSONObject body = root.getJSONObject("body");
                if (body == null){
                    respDto.setState(1);
                }else{
                    String title = body.getString("text");
                    respDto.setFileName(title);
                }
            }
            respDto.setAllow(e.getAllow());
            respDto.setFileId(e.getId());
            response.add(respDto);
        });
    }
}

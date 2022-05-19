package com.yrnet.appweb.business.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yrent.common.constant.FileSuffixConstant;
import com.yrnet.appweb.business.custom.service.IVipInfoService;
import com.yrnet.appweb.business.file.bo.FileConvertBo;
import com.yrnet.appweb.business.file.dto.ConvertLogRequest;
import com.yrnet.appweb.business.file.dto.ConvertLogResponse;
import com.yrnet.appweb.business.file.dto.TransferRequest;
import com.yrnet.appweb.business.file.dto.ConvertResponse;
import com.yrnet.appweb.business.file.entity.ConvertFreeTrialRecord;
import com.yrnet.appweb.business.file.entity.ConvertLog;
import com.yrnet.appweb.business.file.mapper.ConvertLogMapper;
import com.yrnet.appweb.business.file.service.IConvertFreeTrialRecordService;
import com.yrnet.appweb.business.file.service.IConvertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yrnet.appweb.business.file.service.TransferApi;
import com.yrnet.appweb.business.login.entity.WxUser;
import com.yrnet.appweb.business.login.service.WxUserService;
import com.yrnet.appweb.common.exception.DocumentException;
import com.yrnet.appweb.common.http.TransferResponse;
import com.yrnet.appweb.common.properties.ViewWebProperties;
import com.yrnet.appweb.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yrent.common.constant.ConvertType;
import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dengbp
 */
@Service
@Slf4j
public class ConvertServiceImpl extends ServiceImpl<ConvertLogMapper, ConvertLog> implements IConvertService {

    @Autowired
    private ViewWebProperties viewWebProperties;
    @Autowired
    private WxUserService wxUserServiceImpl;
    @Autowired
    private TransferApi transferApi;
    @Autowired
    private ConvertLogMapper convertLogMapper;
    @Resource
    private IVipInfoService vipInfoService;
    @Resource
    private IConvertFreeTrialRecordService convertFreeTrialRecordService;

    private static  Integer FAIL = 1;

    private static int FREE_TIMES = 3;



    @Override
    public List<ConvertLogResponse> queryLog(ConvertLogRequest dto) throws DocumentException {
        LambdaQueryWrapper<ConvertLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConvertLog::getUserOpenId,dto.getOpenId());
        queryWrapper.orderByDesc(ConvertLog::getCreateTime);
        List<ConvertLog> logs = this.list(queryWrapper);
        WxUser user = wxUserServiceImpl.queryByOpenId(dto.getOpenId());
        if (user == null || logs.isEmpty()){
            return new ArrayList<>();
        }
        return this.returnFileList(logs,user);
    }

    @Override
    public ConvertLogResponse transfer(FileConvertBo bo) throws DocumentException {
        ConvertLog clog =  null;
        try {
            StringBuffer filePaths = new StringBuffer();
            for (File f : bo.getFiles()){
                filePaths.append(f.getAbsolutePath()).append(",");
            }
            clog = bo.builderEntity();
            clog.setFilePath(filePaths.substring(0,filePaths.length() - 1));
            convertLogMapper.insert(clog);
            ConvertLogResponse response = clog.transformToRes();
            response.setCreateTime(clog.getCreateTime());
            TransferResponse tfResponse = transferApi.convert(TransferRequest.builder().fileId(clog.getId()).filePath(clog.getFilePath()).fileName(clog.getFileName()).toType(clog.getToType()).build());
            if(!tfResponse.isNormal()){
                clog.setState(FAIL);
                this.updateById(clog);
                return response;
            }
            if (!vipInfoService.isVipUser(bo.getOpenId())){
                ConvertFreeTrialRecord record = ConvertFreeTrialRecord.builder()
                        .setFilePath(clog.getFilePath())
                        .setUserOpenId(clog.getUserOpenId())
                        .setToType(clog.getToType())
                        .setCreateTime(Long.parseLong(DateUtil.current_yyyyMMddHHmmss()));
                convertFreeTrialRecordService.save(record);
            }
            return response;
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            if (clog != null && clog.getId() != null){
                clog.setState(FAIL);
                this.updateLog(clog);
            }
            throw new DocumentException(e.getMessage());
        }
    }

    private void updateLog(ConvertLog log){
        this.updateById(log);
    }

    @Override
    public boolean canFreeTrial(String openId) throws DocumentException {
        int count = convertFreeTrialRecordService.count(new LambdaQueryWrapper<ConvertFreeTrialRecord>().eq(ConvertFreeTrialRecord::getUserOpenId,openId));
        return count>FREE_TIMES?false:true;
    }

    private List<ConvertLogResponse> returnFileList(List<ConvertLog> logs,WxUser user){
        List<ConvertLogResponse> response = new ArrayList<>();
        if(logs == null){
            return response;
        }
        for (ConvertLog l : logs){
            StringBuffer newFilePaths = new StringBuffer();
            if (StringUtils.isNotBlank(l.getNewFilePath())){
                List<String> paths = Arrays.asList(l.getNewFilePath().trim().split(","));
                paths.forEach(path->newFilePaths.append(viewWebProperties.getUrl_base()).append(path.substring(path.lastIndexOf("/"))).append(","));
            }
            try {
                String fileName = l.getFileName();
                String suffix = getSuffix(l.getToType());
                if (StringUtils.isNotBlank(suffix)){
                    fileName =fileName.substring(0,l.getFileName().lastIndexOf(".")).concat(suffix);
                }
                response.add(ConvertLogResponse.builder()
                        .allow(0)
                        .createTime(l.getCreateTime())
                        .createTimeStr(DateUtil.getDateFormat(DateUtil.stringToDate(l.getCreateTime() + "", DateUtil.FULL_TIME_PATTERN), DateUtil.FULL_TIME_SPLIT_PATTERN2))
                        .fileId(l.getId())
                        .url(StringUtils.isNotBlank(newFilePaths)?newFilePaths.substring(0, newFilePaths.length() - 1):"")
                        .useName(user.getWxUserName())
                        .fileName(fileName)
                        .fileSize(l.getFileSize())
                        .state(l.getState()
                        ).build());
            } catch (ParseException e) {
                e.printStackTrace();
            }
                }
        return response;
    }

    private String getSuffix( Integer type){
        String suffix =  "";
        ConvertType toType = ConvertType.getByCode((char)type.intValue());
        switch (toType){
            case word_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            case pdf_to_word:
                suffix = FileSuffixConstant.DOC;
                break;
            case pdf_to_docx:
                suffix = FileSuffixConstant.DOCX;
                break;
            case excel_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            case pdf_to_excel:
                suffix = FileSuffixConstant.EXCEL;
                break;
            case ppt_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            case pdf_to_ppt:
                suffix = FileSuffixConstant.PPT;
                break;
            case pic_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            case pdf_to_jpg:
                suffix = FileSuffixConstant.JPG;
                break;
            case pdf_to_png:
                suffix = FileSuffixConstant.PNG;
                break;
            case docx_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            case odt_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            case doc_to_pdf:
                suffix = FileSuffixConstant.PDF;
                break;
            default:
                log.warn("no type match!");
                break;
        }
        return suffix;
    }
}

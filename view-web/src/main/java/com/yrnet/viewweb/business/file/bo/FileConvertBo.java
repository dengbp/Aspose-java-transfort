package com.yrnet.viewweb.business.file.bo;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dengbp
 * @ClassName FileConvertBo
 * @Description TODO
 * @date 4/18/22 2:01 PM
 */
@Builder
@Data
public class FileConvertBo {

    private MultipartFile file;
    private int toType;
    private String openId;
}

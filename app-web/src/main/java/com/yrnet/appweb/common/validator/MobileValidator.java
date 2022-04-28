package com.yrnet.appweb.common.validator;

import com.yrnet.appweb.common.annotation.IsMobile;
import com.yrnet.appweb.common.entity.RegexpConstant;
import com.yrnet.appweb.common.utils.LicenseUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author dengbp
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return LicenseUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}

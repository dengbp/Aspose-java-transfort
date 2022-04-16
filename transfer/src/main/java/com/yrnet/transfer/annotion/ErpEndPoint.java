package com.yrnet.transfer.annotion;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author dengbp
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ErpEndPoint {
    @AliasFor(annotation = Component.class)
    String value() default "";
}

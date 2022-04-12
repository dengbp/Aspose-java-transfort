package com.yrnet.viewweb.common.mapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 *
 * @author dengbp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Seq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate createTime;

    private Long seq;


}

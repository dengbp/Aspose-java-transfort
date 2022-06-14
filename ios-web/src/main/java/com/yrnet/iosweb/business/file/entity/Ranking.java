package com.yrnet.iosweb.business.file.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dengbp
 * @ClassName Ranking
 * @Description TODO
 * @date 2/4/21 1:28 PM
 */
@Data
public class Ranking  implements Serializable {

    private String userId;
    private String avatarUrl;
    private Integer handleSum;
    private Integer downloadCount;

}

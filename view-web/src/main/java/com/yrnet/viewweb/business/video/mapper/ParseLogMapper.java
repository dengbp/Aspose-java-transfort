package com.yrnet.viewweb.business.video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.viewweb.business.video.entity.ParseLog;
import com.yrnet.viewweb.business.video.entity.Ranking;

import java.util.List;

/**
 * @author dengbp
 */
public interface ParseLogMapper extends BaseMapper<ParseLog> {

    /**
     * Description 用户去印下载排行
     * @param
     * @return java.util.List<com.yrnet.viewweb.business.video.entity.Ranking>
     * @Author dengbp
     * @Date 1:34 PM 2/4/21
     **/
    List<Ranking> ranking();
}

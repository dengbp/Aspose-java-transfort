package com.yrnet.viewweb.business.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yrnet.viewweb.business.file.entity.ParseLog;
import com.yrnet.viewweb.business.file.entity.Ranking;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dengbp
 */
@Repository
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

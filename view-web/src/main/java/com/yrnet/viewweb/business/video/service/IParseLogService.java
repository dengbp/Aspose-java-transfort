package com.yrnet.viewweb.business.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.viewweb.business.video.dto.*;
import com.yrnet.viewweb.business.video.entity.ParseLog;
import com.yrnet.viewweb.business.video.entity.Ranking;
import com.yrnet.viewweb.common.entity.QueryRequestPage;
import com.yrnet.viewweb.common.exception.YinXXException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author dengbp
 */
public interface IParseLogService extends IService<ParseLog> {

    String HANDLE_SUCCESS = "101";

    /**
     * Description todo
     * @param reqDto
     * @return com.yrnet.viewweb.business.video.dto.VideoParseRespDto
     * @throws YinXXException
     * @Author dengbp
     * @Date 1:06 AM 2/4/21
     **/
    VideoParseRespDto parseVideo(VideoParseReqDto reqDto)throws YinXXException;

    /**
     * Description 校验下载权限
     * @param userId
     * @return boolean
     * @throws YinXXException
     * @Author dengbp
     * @Date 12:07 AM 2/6/21
     **/
    boolean allowDownload(String userId)throws YinXXException;

    /**
     * Description todo
     * @param dto
     * @return void
     * @throws YinXXException
     * @Author dengbp
     * @Date 11:59 AM 2/4/21
     **/

    void updateDownloadRecord(VideoDownloadReqDto dto)throws YinXXException;

    /**
     * Description 处理排行
     * @return List
     * @throws YinXXException
     * @Author dengbp
     * @Date 11:59 AM 2/4/21
     **/

    List<Ranking> ranking()throws YinXXException;

    /**
     * Description todo
     * @param dto
     * @return java.util.List<com.yrnet.viewweb.business.video.dto.ParseLogRespDto>
     * @throws YinXXException
     * @Author dengbp
     * @Date 7:05 PM 2/5/21
     **/
    List<ParseLogRespDto> queryLog(ParseLogReqDto dto)throws YinXXException;

  /**
   * Description 视频列表
   * @param page
   * @return java.util.List<com.yrnet.viewweb.business.video.dto.ParseLogRespDto>
   * @throws YinXXException
   * @Author dengbp
   * @Date 7:24 PM 3/7/21
   **/

    List<ParseLogRespDto> onlineList(QueryRequestPage page)throws YinXXException;


    void refreshImg();

    /**
     * Description 用户多媒体
     * @param file
     * @param relationId 关联源文件id,视频封面使用
     * @param  openId
     * @throws YinXXException
     * @return MultipartFileRespDto
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/

    MultipartFileRespDto saveFile(MultipartFile file, String relationId, String openId)throws YinXXException;

    /**
     * Description 用户多媒体信息
     * @param dto 发布入参
     * @throws YinXXException
     * @return void
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/

    void release(MultipartInfoRequestDto dto)throws YinXXException;



}

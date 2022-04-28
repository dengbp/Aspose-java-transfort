package com.yrnet.appweb.business.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yrnet.appweb.business.video.dto.*;
import com.yrnet.appweb.business.video.entity.ParseLog;
import com.yrnet.appweb.business.video.entity.Ranking;
import com.yrnet.appweb.common.entity.QueryRequestPage;
import com.yrnet.appweb.common.exception.DocumentException;
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
     * @throws DocumentException
     * @Author dengbp
     * @Date 1:06 AM 2/4/21
     **/
    VideoParseRespDto parseVideo(VideoParseReqDto reqDto)throws DocumentException;

    /**
     * Description 校验下载权限
     * @param userId
     * @return boolean
     * @throws DocumentException
     * @Author dengbp
     * @Date 12:07 AM 2/6/21
     **/
    boolean allowDownload(String userId)throws DocumentException;

    /**
     * Description todo
     * @param dto
     * @return void
     * @throws DocumentException
     * @Author dengbp
     * @Date 11:59 AM 2/4/21
     **/

    void updateDownloadRecord(VideoDownloadReqDto dto)throws DocumentException;

    /**
     * Description 处理排行
     * @return List
     * @throws DocumentException
     * @Author dengbp
     * @Date 11:59 AM 2/4/21
     **/

    List<Ranking> ranking()throws DocumentException;

    /**
     * Description todo
     * @param dto
     * @return java.util.List<com.yrnet.viewweb.business.video.dto.ParseLogRespDto>
     * @throws DocumentException
     * @Author dengbp
     * @Date 7:05 PM 2/5/21
     **/
    List<ParseLogRespDto> queryLog(ParseLogReqDto dto)throws DocumentException;

  /**
   * Description 视频列表
   * @param page
   * @return java.util.List<com.yrnet.viewweb.business.video.dto.ParseLogRespDto>
   * @throws DocumentException
   * @Author dengbp
   * @Date 7:24 PM 3/7/21
   **/

    List<ParseLogRespDto> onlineList(QueryRequestPage page)throws DocumentException;


    void refreshImg();

    /**
     * Description 用户多媒体
     * @param file
     * @param relationId 关联源文件id,视频封面使用
     * @param  openId
     * @throws DocumentException
     * @return MultipartFileRespDto
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/

    MultipartFileRespDto saveFile(MultipartFile file, String relationId, String openId)throws DocumentException;

    /**
     * Description 用户多媒体信息
     * @param dto 发布入参
     * @throws DocumentException
     * @return void
     * @Author dengbp
     * @Date 00:42 2020-11-25
     **/

    void release(MultipartInfoRequestDto dto)throws DocumentException;



}

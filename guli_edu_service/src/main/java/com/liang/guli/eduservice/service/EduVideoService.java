package com.liang.guli.eduservice.service;

import com.liang.guli.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.guli.eduservice.entity.dto.VideoDto;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-15
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoById(String courseId);

    List<VideoDto> getEduVideoList(String id, String courseId);

    boolean removeVideoByVideoId(String videoId);

    boolean removeVideoByChapterId(String chapterId);

}

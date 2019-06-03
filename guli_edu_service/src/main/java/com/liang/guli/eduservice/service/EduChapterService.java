package com.liang.guli.eduservice.service;

import com.liang.guli.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.guli.eduservice.entity.dto.ChapterDto;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-15
 */
public interface EduChapterService extends IService<EduChapter> {

    void removeChapterById(String courseId);

    List<ChapterDto> getEduChapterList();

    List<ChapterDto> getEduChapterList(String courseId);

    boolean removeChapterByChapterId(String chapterId);

    EduChapter getChatperById(String chatperId);
}

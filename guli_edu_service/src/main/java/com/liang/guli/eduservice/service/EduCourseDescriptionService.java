package com.liang.guli.eduservice.service;

import com.liang.guli.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-14
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    boolean saveCourseInfo(EduCourseDescription eduCourseDescription);

    List<EduCourseDescription> getCourseDescription();

    void removeCourseDescriptionById(String courseId);
}

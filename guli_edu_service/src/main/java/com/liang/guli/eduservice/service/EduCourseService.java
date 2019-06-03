package com.liang.guli.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.guli.eduservice.entity.dto.CourseInfoForm;
import com.liang.guli.eduservice.entity.dto.CourseWebDto;
import com.liang.guli.eduservice.entity.query.CourseInfoQuery;
import com.liang.guli.eduservice.entity.query.PublishCourseInfo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-14
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInFormById(String id);

    String updateCourseInfo(CourseInfoForm courseInfoForm);

    List<CourseInfoForm> getCourseInfoList();

    boolean removeCourseById(String id);

    void getCourseInfoPageList(Page<EduCourse> eduCoursePage, CourseInfoQuery courseInfoQuery);

    PublishCourseInfo getPublishCourseInfo(String courseId);

    boolean updateCourseStatus(String courseId);

    List<EduCourse> getEduCourseByTeacherId(String teacherId);

    CourseWebDto getCourseWebDtoByCourseId(String courseId);
}

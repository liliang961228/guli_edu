package com.liang.guli.eduservice.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.guli.eduservice.entity.dto.CourseWebDto;
import com.liang.guli.eduservice.entity.query.PublishCourseInfo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author liliang
 * @since 2019-05-14
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishCourseInfo getPublishCourseInfo(String courseId);

    CourseWebDto getCourseWebDtoByCourseId(String courseId);
}

package com.liang.guli.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.guli.eduservice.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageList(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery);
}

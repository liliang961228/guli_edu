package com.liang.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.eduservice.entity.EduCourse;
import com.liang.guli.eduservice.entity.EduCourseDescription;
import com.liang.guli.eduservice.entity.dto.CourseInfoForm;
import com.liang.guli.eduservice.entity.dto.CourseWebDto;
import com.liang.guli.eduservice.entity.query.CourseInfoQuery;
import com.liang.guli.eduservice.entity.query.PublishCourseInfo;
import com.liang.guli.eduservice.handler.EduException;
import com.liang.guli.eduservice.mapper.EduCourseMapper;
import com.liang.guli.eduservice.service.EduChapterService;
import com.liang.guli.eduservice.service.EduCourseDescriptionService;
import com.liang.guli.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.guli.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;


    /**
     * 添加课程到数据库中
     * @param courseInfoForm
     * @return
     */
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        //1 把课程基本信息添加edu_course表里面
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        //如果课程基本信息添加失败
        if (insert<0){

            throw new EduException(20001,"添加课程基本信息失败");

        }
            //2 把课程描述信息添加到edu_course_description表里面
            //向EduCourseDescription对象里面设置数据
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            //课程id
            eduCourseDescription.setId(eduCourse.getId());
            //课程描述
            eduCourseDescription.setDescription(courseInfoForm.getDescription());
            //把描述信息添加课程描述表里面
            boolean b = eduCourseDescriptionService.saveCourseInfo(eduCourseDescription);
            if(!b) {
                throw new EduException(20001,"添加课程描述失败");
            }


            return eduCourse.getId();

    }

    /**
     * 通过id查找到该课程的信息
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseInFormById(String id) {

        //查询得到该id的课程信息
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        EduCourse eduCourse = baseMapper.selectById(id);
        BeanUtils.copyProperties(eduCourse,courseInfoForm);

        //查询的到课程的描述信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        courseInfoForm.setDescription(eduCourseDescription.getDescription());

        return courseInfoForm;
    }

    /**
     * 更新课程信息
     * @param courseInfoForm
     * @return
     */
    @Override
    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i<0){
            throw new EduException(20001,"课程信息保存失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoForm.getId());
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!b){
            throw new EduException(20001,"课程信息保存失败");
        }
        return courseInfoForm.getId();
    }

    /**
     * 查询所有的课程信息
     * @return
     */
    @Override
    public List<CourseInfoForm> getCourseInfoList() {

        //查询到所有的课程

        List<CourseInfoForm> courseInfoForms = new ArrayList<>();
        List<EduCourse> eduCourses = baseMapper.selectList(null);

        //查询到所有的课程的描述
        List<EduCourseDescription> eduCourseDescriptions = eduCourseDescriptionService.getCourseDescription();

        //遍历课程的集合
        for(int i = 0;i<eduCourses.size();i++){
            CourseInfoForm courseInfoForm = new CourseInfoForm();
            BeanUtils.copyProperties(eduCourses.get(i),courseInfoForm);
            //courseInfoForms.add(courseInfoForm);

            //根据相同的courseid，把详细课程信息添加到CourseInfoFrom
            //遍历课程描述集合
            for(int j = 0;j<eduCourseDescriptions.size();j++){
                if(eduCourses.get(i).getId().equals(eduCourseDescriptions.get(j).getId())){

//                  //添加课程详细信息
                    courseInfoForm.setDescription(eduCourseDescriptions.get(j).getDescription());
                }
            }
            //把课程的所有的信息添加到集合中
            courseInfoForms.add(courseInfoForm);
        }
        return courseInfoForms;
    }

    /**
     * 通过ID删除课程信息
     * @param courseId
     * @return
     */
    @Override
    public boolean removeCourseById(String courseId) {
        //从课程表的结构的下面向上面删除

        //删除课程的小节
        eduVideoService.removeVideoById(courseId);

        //删除课程的章节
        eduChapterService.removeChapterById(courseId);

        //删除课程的描述信息
        eduCourseDescriptionService.removeCourseDescriptionById(courseId);

        //删除课程的信息
        int i = baseMapper.deleteById(courseId);
        if(i<=0){
            return false;
        }
        return true;
    }

    /**
     * 带条件分页查询课程信息
     * @param courseInfoQuery
     * @return
     */
    @Override
    public void getCourseInfoPageList(Page<EduCourse> eduCoursePage, CourseInfoQuery courseInfoQuery) {

        //构建条件部分
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        if (courseInfoQuery == null){
            baseMapper.selectPage(eduCoursePage, wrapper);
            return;
        }

        String title = courseInfoQuery.getTitle();
        BigDecimal price = courseInfoQuery.getPrice();
        Integer lessonNum = courseInfoQuery.getLessonNum();
        Date gmtCreate = courseInfoQuery.getGmtCreate();
        Date gmtModified = courseInfoQuery.getGmtModified();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }

        if (!StringUtils.isEmpty(price)){
            wrapper.like("price",price);
        }

        if (!StringUtils.isEmpty(lessonNum)){
            wrapper.like("lesson_num",lessonNum);
        }

        if (!StringUtils.isEmpty(gmtCreate)){
            wrapper.like("gmt_create",gmtCreate);
        }

        if (!StringUtils.isEmpty(gmtModified)){
            wrapper.like("gmt_modified",gmtModified);
        }

        baseMapper.selectPage(eduCoursePage,wrapper);

    }

    /**
     * 根据课程ID查询所有课程信息
     * @param courseId
     * @return
     */
    @Override
    public PublishCourseInfo getPublishCourseInfo(String courseId) {
        PublishCourseInfo publishCourseInfo = baseMapper.getPublishCourseInfo(courseId);
        return publishCourseInfo;
    }

    /**
     * 发布课程，修改课程的状态码
     * @param courseId
     * @return
     */
    @Override
    public boolean updateCourseStatus(String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        int i = baseMapper.updateById(eduCourse);
        return i>0;
    }

    /**
     *根据 讲师id查询到讲师的所有课程
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> getEduCourseByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    /**
     * 通过课程id查找课程的详细信息
     * @param courseId
     * @return
     */
    @Override
    public CourseWebDto getCourseWebDtoByCourseId(String courseId) {

        CourseWebDto courseWebDto = baseMapper.getCourseWebDtoByCourseId(courseId);
        return courseWebDto;
    }


}

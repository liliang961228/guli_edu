package com.liang.guli.eduservice.service.impl;

import com.liang.guli.eduservice.entity.EduCourseDescription;
import com.liang.guli.eduservice.mapper.EduCourseDescriptionMapper;
import com.liang.guli.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-14
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    /**
     * 把课程的详细的描述信息添加到数据库的课程描述表中
     * @param eduCourseDescription
     * @return
     */
    @Override
    public boolean saveCourseInfo(EduCourseDescription eduCourseDescription) {
        //把传入的课程描述信息保存在数据库中
        int insert = baseMapper.insert(eduCourseDescription);
        if (insert>0){
            return true;
        }
        return false;
    }

    /**
     * 查询所有的课程描述信息
     * @return
     */
    @Override
    public List<EduCourseDescription> getCourseDescription() {
        //查询所有的课程信息
        List<EduCourseDescription> eduCourseDescriptions = baseMapper.selectList(null);

        return eduCourseDescriptions;
    }

    /**
     * 删除课程的描述信息
     * @param courseId
     */
    @Override
    public void removeCourseDescriptionById(String courseId) {
        baseMapper.deleteById(courseId);
    }
}

package com.liang.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.eduservice.entity.EduTeacher;
import com.liang.guli.eduservice.entity.query.TeacherQuery;
import com.liang.guli.eduservice.mapper.EduTeacherMapper;
import com.liang.guli.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Queue;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-07
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    /**
     * 多条件组合查询分页
     * @param pageTeacher
     * @param teacherQuery
     */
    @Override
    public void pageList(Page<EduTeacher> pageTeacher, TeacherQuery teacherQuery) {



        //构建条件部分
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        if(teacherQuery==null){
            baseMapper.selectPage(pageTeacher,queryWrapper);
            return;
        }

        //获取条件值
        String name = teacherQuery.getName();
        String level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_modified",end);
        }

        baseMapper.selectPage(pageTeacher,queryWrapper);



    }
}

package com.liang.guli.eduservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liang.guli.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author liliang
 * @since 2019-05-13
 */
public interface EduSubjectMapper extends BaseMapper<EduSubject> {

    EduSubject selectOne(QueryWrapper<Object> wrapper);
}

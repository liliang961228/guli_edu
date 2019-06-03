package com.liang.guli.eduservice.entity.query;

import lombok.Data;

@Data
public class PublishCourseInfo {

    private String title;

    private String cover;

    private Integer lessonNum;//课时数

    private String subjectLevelOne;//一级分类名称

    private String subjectLevelTwo;//二级分类名称

    private String teacherName;//讲师名称

    private String price;//价格只用于显示
}

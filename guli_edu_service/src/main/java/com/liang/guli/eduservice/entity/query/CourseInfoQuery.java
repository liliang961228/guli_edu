package com.liang.guli.eduservice.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInfoQuery {

    private String title;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer lessonNum;

    private BigDecimal price;
}

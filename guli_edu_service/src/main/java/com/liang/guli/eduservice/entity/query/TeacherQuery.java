package com.liang.guli.eduservice.entity.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherQuery {

    private String name;
    private String level;
    private String begin;
    private String end;

}

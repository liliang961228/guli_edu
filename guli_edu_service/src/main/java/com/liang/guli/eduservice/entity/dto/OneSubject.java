package com.liang.guli.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {

    private String id;

    private String title;

    List<TwoSubject> list = new ArrayList<>();

}

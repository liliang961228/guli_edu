package com.liang.guli.eduservice.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoDto {

    private String id;

    private String title;

    @ApiModelProperty(value = "视频资源")
    private String videoSourceId;
}

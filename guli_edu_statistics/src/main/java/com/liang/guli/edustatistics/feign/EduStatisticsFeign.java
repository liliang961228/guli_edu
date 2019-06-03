package com.liang.guli.edustatistics.feign;

import com.liang.guli.educommon.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-edu-center")
@Component
public interface EduStatisticsFeign {

    @GetMapping("/educenter/center-member/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day);
}

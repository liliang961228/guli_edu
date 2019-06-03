package com.liang.guli.eduservice.feign;

import com.liang.guli.educommon.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//指定调用的服务的名称
@FeignClient("guli-edu-videoupdate")
@Component

public interface EduVideoFeign {

    //定义方法，指定调用路径
    //如果在注册中心中进行服务之间的调用，@PathVariable这个注解里面必须加参数
    @DeleteMapping("/eduVideoUpload/videoUploadController/deleteVideo/{videoSourceId}")
    public R deleteVideo(@PathVariable("videoSourceId") String videoSourceId);

    //批量删除上传到云端的视频
    @DeleteMapping("/eduVideoUpload/videoUploadController/deleteVideoByvideoSourceIdLists")
    public R deleteVideoByvideoSourceIdLists(@RequestParam("videoSourceIdLists") List<String> videoSourceIdLists);

}

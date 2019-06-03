package com.liang.guli.eduservice.controller;


import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduVideo;
import com.liang.guli.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-15
 */
@Api(tags = "章节小节测试")
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 增加章节的小节信息在数据库中
     */
    @ApiOperation(value = "增加章节的小节信息到数据库中")
    @PostMapping("saveEduVideo")
    public R saveEduVideo(@RequestBody EduVideo eduVideo){

        boolean save = eduVideoService.save(eduVideo);
        if (save){
            return R.success();
        }else {
            return R.error();
        }

    }

    /**
     * 删除章节中小节信息
     */
    @ApiOperation(value = "删除章节中的小节信息")
    @DeleteMapping("deleteEduVideo/{videoId}")
    public R deleteEduVideo(@PathVariable String videoId){
        boolean b = eduVideoService.removeVideoByVideoId(videoId);
        if (b){
            return R.success();
        }else {
            return R.error();
        }
    }


    /**
     * 修改章节的小节信息
     */
    @ApiOperation(value = "修改章节的小节信息")
    @PostMapping("updateEduVideo")
    public R updateEduVideo(@RequestBody EduVideo eduVideo){
        boolean b = eduVideoService.updateById(eduVideo);
        if (b){
            return R.success();
        }else {
            return R.error();
        }
    }

    /**
     * 查询章节中的小节信息
     */
    @ApiOperation(value = "查询章节的小节信息")
    @GetMapping("getEduVideoById/{videoId}")
    public R getEduVideoById(@PathVariable String videoId){
        EduVideo video = eduVideoService.getById(videoId);
        if (video != null){
            return R.success().data("eduVideo",video);
        }else {
            return R.error();
        }
    }

}


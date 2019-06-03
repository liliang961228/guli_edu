package com.liang.guli.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduCourse;
import com.liang.guli.eduservice.entity.dto.CourseInfoForm;
import com.liang.guli.eduservice.entity.query.CourseInfoQuery;
import com.liang.guli.eduservice.entity.query.PublishCourseInfo;
import com.liang.guli.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-14
 */
@Api(tags = "课程发布测试")
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    /**
     * 添加课程信息，并且返回添加课程的ID
     * @param courseInfoForm
     * @return
     */
    @ApiOperation(value = "添加课程到数据库中")
    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoForm courseInfoForm){

        String courseId = eduCourseService.saveCourseInfo(courseInfoForm);

            return R.success().message("添加课程成功").data("courseId",courseId);

    }

    /**
     * 通过id查询到添加课程的信息
     * @param id
     * @return
     */
    @GetMapping("getCourseInFormById/{id}")
    public R getCourseInFormById(@PathVariable String id){
        CourseInfoForm item = eduCourseService.getCourseInFormById(id);
        return R.success().data("item",item);

    }

    /**
     * 更新数据库中的课程信息
     * @param courseInfoForm
     * @return
     */
    @ApiOperation(value = "更新数据库中的课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm){
        String courseId = eduCourseService.updateCourseInfo(courseInfoForm);
        return R.success().data("courseId",courseId);
    }

    /**
     * 查询所有的课程信息
     * @return
     */
    @ApiOperation(value = "查询所有的课程信息")
    @GetMapping("getCourseInfoList")
    public R getCourseInfoList(){

        List<CourseInfoForm> items = eduCourseService.getCourseInfoList();
        return R.success().data("items",items);
    }


    @ApiOperation(value = "删除指定id的课程信息")
    @DeleteMapping("deleteCourseInfoById/{courseId}")
    public R deleteCourseInfoById(@PathVariable String courseId){

        boolean b = eduCourseService.removeCourseById(courseId);

        if (b){
            return R.success().message("删除课程信息成功");
        }else {
         return R.error().message("删除课程信息失败");
        }
    }


    /**
     * 带条件分页查询课程信息
     * @return
     */
    @ApiOperation(value = "带条件分页查询课程信息")
    @PostMapping("getCourseInfoPageList/{page}/{limit}")
    public R getCourseInfoPageList(@PathVariable Integer page,
                                    @PathVariable Integer limit,
                                      @RequestBody(required = false) CourseInfoQuery courseInfoQuery){

        //创建一个page对象，把page和limit传入
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        eduCourseService.getCourseInfoPageList(eduCoursePage,courseInfoQuery);

        List<EduCourse> records = eduCoursePage.getRecords();
        long total = eduCoursePage.getTotal();

        return R.success().data("rows",records).data("total",total);
    }

    /**
     * 根据课程ID查询所有课程信息
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程ID查询所有课程信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        System.out.println(courseId);
        PublishCourseInfo publishCourseInfo = eduCourseService.getPublishCourseInfo(courseId);
        return R.success().data("item",publishCourseInfo);

    }

    /**
     * 发布课程，修改课程的状态码
     * @param courseId
     * @return
     */

    @ApiOperation(value = "发布课程，修改课程的状态码")
    @PutMapping("updateCourseStatus/{courseId}")
    public R updateCourseStatus(@PathVariable String courseId){
        boolean flag = eduCourseService.updateCourseStatus(courseId);
        if (flag){
            return R.success().data("message","课程发布成功");
        }else {
            return R.error().data("message","课程发布失败");
        }
}


}


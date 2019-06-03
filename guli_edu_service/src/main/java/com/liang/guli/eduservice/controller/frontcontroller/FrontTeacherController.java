package com.liang.guli.eduservice.controller.frontcontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduCourse;
import com.liang.guli.eduservice.entity.EduTeacher;
import com.liang.guli.eduservice.service.EduCourseService;
import com.liang.guli.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api(tags = "前端讲师的测试")
@RestController
@RequestMapping("/eduservice/Front-edu-teacher")
@CrossOrigin
public class FrontTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;


    /**
     * 查询所有的讲师
     * @return
     */
    @ApiOperation(value = "查询所有的讲师")
    @GetMapping("getFrontTeacherLists")
    public R getFrontTeacherLists() {

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.success().data("items",list);
    }

    /**
     * 分页查询讲师信息
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("getFrontTeacherPageLists/{page}/{limit}")
    public R getFrontTeacherPageLists(@PathVariable long page,
                                      @PathVariable long limit){

        Page<EduTeacher> objectPage = new Page<>(page,limit);
        eduTeacherService.pageList(objectPage,null);

        List<EduTeacher> records = objectPage.getRecords();//讲师列表集合
        long total = objectPage.getTotal();//总的记录数
        long size = objectPage.getSize();//每页显示的记录数
        long current = objectPage.getCurrent();//当前页
        boolean hasPrevious = objectPage.hasPrevious();//是否有上一页
        boolean hasNext = objectPage.hasNext();//是否有下一页
        long pages = objectPage.getPages();//总页数

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("size",size);
        map.put("current",current);
        map.put("previous",hasPrevious);
        map.put("next",hasNext);
        map.put("items",records);
        map.put("pages",pages);

        return R.success().data(map);
    }

    @ApiOperation(value = "根据讲师id查询到具体的讲师信息")
    @GetMapping("getFrontTeacherById/{teacherId}")
    public R getFrontTeacherById(@PathVariable String teacherId){

        //通过id查询到讲师的信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);

        //查询讲师的课程信息
       List<EduCourse> courseList = eduCourseService.getEduCourseByTeacherId(teacherId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher",teacher);
        map.put("courseList",courseList);
        return R.success().data(map);
    }
}

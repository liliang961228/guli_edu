package com.liang.guli.eduservice.controller.frontcontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduCourse;
import com.liang.guli.eduservice.entity.dto.ChapterDto;
import com.liang.guli.eduservice.entity.dto.CourseWebDto;
import com.liang.guli.eduservice.service.EduChapterService;
import com.liang.guli.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "前端课程的测试")
@RestController
@RequestMapping("/eduservice/Front-edu-course")
@CrossOrigin
public class FrontCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 分页查询课程
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询课程")
    @GetMapping("getFrontCoursePageLists/{page}/{limit}")
    public R getFrontCoursePageLists(@PathVariable Integer page,
                                     @PathVariable Integer limit){


        Page<EduCourse> coursePage = new Page<>(page,limit);
        eduCourseService.getCourseInfoPageList(coursePage,null);

        List<EduCourse> records = coursePage.getRecords();//课程信息的集合
        long total = coursePage.getTotal();//总的记录数
        long current = coursePage.getCurrent();//当前页
        long size = coursePage.getSize();//当前页的显示条数
        boolean next = coursePage.hasNext();//是否有下一页
        boolean previous = coursePage.hasPrevious();//是否有上一页
        long pages = coursePage.getPages();//总的页数

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("next",next);
        map.put("previous",previous);
        map.put("records",records);
        map.put("pages",pages);

        return R.success().data(map);
    }

    @ApiOperation(value = "前端通过课程ID查询到详细的课程信息")
    @GetMapping("getCourseWebDtoByCourseId/{courseId}")
    public R getCourseWebDtoByCourseId(@PathVariable String courseId){

        //通过课程id查找课程的详细信息
        CourseWebDto courseWebDto = eduCourseService.getCourseWebDtoByCourseId(courseId);

        //查找到课程的详细章节信息
        List<ChapterDto> chapterList = eduChapterService.getEduChapterList(courseId);

        Map<String, Object> map = new HashMap<>();
        map.put("courseWebDto",courseWebDto);
        map.put("chapterList",chapterList);

        return R.success().data(map);
    }
}

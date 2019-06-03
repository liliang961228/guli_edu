package com.liang.guli.eduservice.controller;


import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduSubject;
import com.liang.guli.eduservice.entity.dto.OneSubject;
import com.liang.guli.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-13
 */

@Api(tags = "课程列表分类操作的基本功能")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     * 上传文件，在edu_subject表中添加对应的数据
     * @param file
     * @return
     */
    @ApiOperation(value = "上传文件在数据库中保存课程分类")
    @PostMapping("uploadFile")
    public R uploadFile(@RequestParam("file") MultipartFile file){

        List<String> message = eduSubjectService.uploadFile(file);

        if(message.size()>0){
            return R.error().message("导入失败").data("message",message);
        }else {
            return R.success();
        }
    }

    /**
     *查询所有的列表
     * @return
     */
    @ApiOperation(value = "查询所有的课程分类")
    @GetMapping("getSubjectList")
    public R getSubjectList(){

        List<OneSubject> subjectList = eduSubjectService.getSubjectList();
        return R.success().data("subjectList",subjectList);
    }

    /**
     * 通过id删除课程
     * @param id
     * @return
     */
    @ApiOperation(value = "删除课程中分类的数据")
    @DeleteMapping("deleteSubjectById/{id}")
    public R deleteSubjectById(@PathVariable String id){

        boolean b = eduSubjectService.deleteSubjectById(id);
        if(b){
            return R.success().message("删除成功");
        }else {
            return  R.error().message("删除失败");
        }

    }


    /**
     *增加课程一级分类
     * @param eduSubject
     * @return
     */
    @ApiOperation(value = "添加课程一级分类")
    @PostMapping("saveOneSubject")
    public R saveOneSubject(@RequestBody EduSubject eduSubject){

        boolean b = eduSubjectService.saveOneSubject(eduSubject);
        if (b){
            return R.success().message("添加一级分类成功");
        }
        return R.error().message("添加一级分类失败");

    }

    /**
     * 增加课程二级分类
     * @param eduSubject
     * @return
     */
    @ApiOperation(value = "添加课程二级分类")
    @PostMapping("saveTwoSubject")
    public R saveTwoSubject(@RequestBody EduSubject eduSubject){
        boolean b = eduSubjectService.saveTwoSubject(eduSubject);
        if (b){
            return R.success().message("添加二级分类成功");
        }
        return R.error().message("添加二级分类失败");
    }

}


package com.liang.guli.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduTeacher;
import com.liang.guli.eduservice.entity.query.TeacherQuery;
import com.liang.guli.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-07
 */
@Api(tags = "讲师模块的基本功能")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //登录方法
    @GetMapping("login")
    public R login(){
        return R.success().data("token","admin");
    }

    //获取信息的方法
    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    @GetMapping("info")
    public R info(){
        return  R.success()
                    .data("roles","[admin]")
                    .data("name","admin")
                    .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    /**
     * 查询所有的讲师
     *
     * @return
     */
    @ApiOperation(value = "查询所有的讲师")
    @GetMapping("allTeacherList")
    public R allTeacherList() {

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.success().data("items",list);
    }

    /**
     * 逻辑删除一名教师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除一名讲师")
    @DeleteMapping("deleteTeacherById/{id}")
    @ResponseBody
    public R deleteTeacherById(@PathVariable String id) {

        boolean b = eduTeacherService.removeById(id);
        if (b) {
            return R.success();
        } else {
            return R.error();
        }
    }

    /**
     * 不带条件分页查找
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "不带条件分页查找")
    @PostMapping("pageSelectTeacher/{page}/{limit}")
    public R pageSelectTeacher(@PathVariable Integer page,
                               @PathVariable Integer limit) {
        //创建page对象，传入当前页和当前页的显示数量
        Page<EduTeacher> page1 = new Page<>(page, limit);

        //调用方法实现分页
        eduTeacherService.page(page1, null);

        List<EduTeacher> records = page1.getRecords();
        long total = page1.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.success().data(map);
    }


    /**
     * 多条件组合查询分页
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "多条件组合查询分页")
    @PostMapping("pageConditionList/{page}/{limit}")
    public R pageConditionList(@PathVariable Integer page,
                               @PathVariable Integer limit,
                               @RequestBody(required = false) TeacherQuery teacherQuery){

        //创建一个page对象，把page和limit传入
        Page<EduTeacher> PageTeacher = new Page<>(page,limit);
        //定义了一个方法完成多条件组合查询分页
        eduTeacherService.pageList(PageTeacher,teacherQuery);

        List<EduTeacher> records = PageTeacher.getRecords();
        long total = PageTeacher.getTotal();
        return R.success().data("rows",records).data("total",total);

    }

    /**
     * 添加讲师功能的方法
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "添加讲师功能的方法")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){

        boolean save = eduTeacherService.save(eduTeacher);
//        System.out.println(save);
        if(save){
            return R.success();
        }else {
            return R.error();
        }

    }


    /**
     * 根据讲师id查询讲师信息，为了修改做数据回显使用
     * @param id
     * @return
     */
    @ApiOperation(value = "根据讲师id查询讲师信息，为了修改做数据回显使用")
    @GetMapping("getTeacherInfoById/{id}")
    public R getTeacherInfoById(@PathVariable String id){
        EduTeacher teacher = eduTeacherService.getById(id);

        return R.success().data("teacher",teacher);
    }


    /**
     * 修改讲师的操作
     * @param eduTeacher
     * @return
     */
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.success();
        }else {
            return R.error();
        }
    }




}


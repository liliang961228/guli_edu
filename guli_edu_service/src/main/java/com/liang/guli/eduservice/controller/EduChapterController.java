package com.liang.guli.eduservice.controller;


import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduChapter;
import com.liang.guli.eduservice.entity.dto.ChapterDto;
import com.liang.guli.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author liliang
 * @since 2019-05-15
 */
@Api(tags = "课程章节测试")
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 查询所有的章节及章节下的小节
     * @return
     */
    @ApiOperation(value = "查询所有的章节及对应的小节")
    @GetMapping("getChapterDtoList")
    public R getEduChapterList(){

        List<ChapterDto> items = eduChapterService.getEduChapterList();

        return R.success().data("items",items);
    }

    /**
     * 根据课程id查询到该课程的所有章节和所有小节
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程id查询到该课程的所有章节和所有小节")
    @GetMapping("getChapterDtoByid/{courseId}")
    public R getChapterDtoByid(@PathVariable String courseId){
        List<ChapterDto> items = eduChapterService.getEduChapterList(courseId);
        return R.success().data("items",items);
    }

    /**
     * 根据chapterId查询章节的信息
     * @param chatperId
     * @return
     */
    @ApiOperation(value = "根据章节id查询章节信息")
    @GetMapping("getChatperById/{chatperId}")
    public R getChatperById(@PathVariable String chatperId){
        EduChapter eduChapter = eduChapterService.getChatperById(chatperId);
        return R.success().data("chapter",eduChapter);

    }

    /**
     * 增加课程的章节信息到数据库中
     * @param eduChapter
     * @return
     */
    @ApiOperation(value = "增加课程的章节信息到数据库中")
    @PostMapping("savaEduChapter")
    public R savaEduChapter(@RequestBody EduChapter eduChapter){
        boolean save = eduChapterService.save(eduChapter);
        if (save){
            return R.success().data("message","增加课程的章节信息成功");
        }else {
            return R.error().data("message","增加课程的章节的信息失败");
        }
    }

    /**
     * 删除课程章节数据库中的数据
     * @param chapterId
     * @return
     */
    @ApiOperation(value = "删除课程章节数据库中的数据")
    @DeleteMapping("deleteEduChapterById/{chapterId}")
    public R deleteEduChapterById(@PathVariable String chapterId){
        boolean b = eduChapterService.removeChapterByChapterId(chapterId);
        if (b){
            return R.success().data("message","删除课程章节的信息成功");
        }else {
            return R.error().data("message","删除课程章节的信息失败");
        }

    }

    /**
     * 修改课程章节数据库中的数据
     * @param eduChapter
     * @return
     */
    @ApiOperation(value = "修改课程章节数据库中的数据")
    @PostMapping("updateEduChapter")
    public R updateEduChapter(@RequestBody EduChapter eduChapter){
        boolean b = eduChapterService.updateById(eduChapter);
        if (b){
            return R.success();
        }else {
            return R.error();
        }

    }

}


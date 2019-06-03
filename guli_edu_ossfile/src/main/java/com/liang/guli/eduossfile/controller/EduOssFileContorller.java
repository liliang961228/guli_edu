package com.liang.guli.eduossfile.controller;

import com.liang.guli.educommon.R;
import com.liang.guli.eduossfile.service.EduOssFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传模块")
@RestController
@RequestMapping("/eduossfile/edu-ossfileupload")
@CrossOrigin
public class EduOssFileContorller {

    @Autowired
    private EduOssFileService eduOssFileService;

    @PostMapping("ossFileUpload")
    @ApiOperation(value = "文件上传功能接口")
    public R ossFileUpload(@RequestParam("file") MultipartFile file){

        String url = eduOssFileService.upload(file);
        return R.success().data("url",url);
    }


}

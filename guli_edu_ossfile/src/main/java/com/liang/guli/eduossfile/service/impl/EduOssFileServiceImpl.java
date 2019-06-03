package com.liang.guli.eduossfile.service.impl;

import com.aliyun.oss.OSSClient;
import com.liang.guli.eduossfile.service.EduOssFileService;
import com.liang.guli.eduossfile.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class EduOssFileServiceImpl implements EduOssFileService {


    /**
     * 上传图片到阿里云oss中
     * @param file
     */
    @Override
    public String upload(MultipartFile file) {

        try {
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(ConstantPropertiesUtil.END_POINT,
                    ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);

            //获取文件上传流
            InputStream inputStream = file.getInputStream();

            //构建日期路径：
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //获取文件名称
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            String fileUrl = filePath+"/"+fileName+originalFilename;


            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。

            //<yourBucketName>:oss中bucket桶的名称
            //<yourObjectName>:上传到OSSz中是什么名称
            //new File("<yourLocalFile>：上传文件的名称
            // ossClient.putObject("<yourBucketName>", "<yourObjectName>", new File("<yourLocalFile>"));
            ossClient.putObject(ConstantPropertiesUtil.BUCKET_NAME, fileUrl,inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //http://guliedu1205.oss-cn-beijing.aliyuncs.com/0511/111.png
            String path = "http://"+ConstantPropertiesUtil.BUCKET_NAME+"."+ConstantPropertiesUtil.END_POINT+
                                "/"+fileUrl;

            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

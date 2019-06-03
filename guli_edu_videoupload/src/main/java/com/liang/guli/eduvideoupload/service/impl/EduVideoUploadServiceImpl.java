package com.liang.guli.eduvideoupload.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.liang.guli.eduvideoupload.service.EduVideoUploadService;
import com.liang.guli.eduvideoupload.utils.AliyunVodSDKUtils;
import com.liang.guli.eduvideoupload.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class EduVideoUploadServiceImpl implements EduVideoUploadService {

    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @Override
    public String videoUpload(MultipartFile file) {
        try {
            //阿里云的keyId
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            //阿里云的keySecret
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

            //文件输入流
            InputStream inputStream = file.getInputStream();

            //文件名称
            String fileName = file.getOriginalFilename();

            String title = fileName.substring(0, fileName.lastIndexOf("."));

            UploadStreamRequest request =
                    new UploadStreamRequest(accessKeyId,accessKeySecret, title, fileName, inputStream);


            UploadVideoImpl uploader = new UploadVideoImpl();

            //调用方法实现文件上传
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();

            } else {
                //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * 删除阿里云上传的小节视频
     * @param videoSourceId
     * @return
     */
    @Override
    public boolean deleteVideoByVideoSourceId(String videoSourceId) {

        try {
            //初始化对象
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoSourceId);

            //调用方法实现删除的功能
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");

            return true;

        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }


    }


    /**
     * 批量删除上传到云端的视频
     * @param videoSourceIdLists
     * @return
     */
    @Override
    public boolean deleteVideoByvideoSourceIdLists(List<String> videoSourceIdLists) {
        try {

            //初始化对象
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //传入多个视频ID，多个用逗号分隔   1,2,3
            //join方法两个参数：第一个参数数组，第二个参数把数组使用什么符号进行分割
            String videoSourceId = StringUtils.join(videoSourceIdLists.toArray(), ",");


            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //传入多个视频ID，多个用逗号分隔
            request.setVideoIds(videoSourceId);

            //调用方法实现删除的功能
            DeleteVideoResponse response = client.getAcsResponse(request);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}



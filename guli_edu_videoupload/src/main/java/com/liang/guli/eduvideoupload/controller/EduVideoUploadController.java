package com.liang.guli.eduvideoupload.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.liang.guli.educommon.R;
import com.liang.guli.eduvideoupload.service.EduVideoUploadService;
import com.liang.guli.eduvideoupload.utils.AliyunVodSDKUtils;
import com.liang.guli.eduvideoupload.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "课程小节视频功能测试")
@RestController
@RequestMapping("/eduVideoUpload/videoUploadController")
@CrossOrigin
public class EduVideoUploadController {

    @Autowired
    private EduVideoUploadService eduVideoUploadService;

    /**
     * 批量删除云端上传的视频
     *@param videoSourceIdLists
     * @return
     */
    @ApiOperation(value = "批量删除云端上传的视频")
    @DeleteMapping("deleteVideoByvideoSourceIdLists")
    public R deleteVideoByvideoSourceIdLists(@RequestParam("videoSourceIdLists") List<String> videoSourceIdLists){
        boolean flag =
                eduVideoUploadService.deleteVideoByvideoSourceIdLists(videoSourceIdLists);
        return R.error();

    }
    /**
     * 删除阿里云中上传的视频
     * @param videoSourceId
     * @return
     */
    @ApiOperation(value = "课程小节视频删除功能测试")
    @DeleteMapping("deleteVideo/{videoSourceId}")
    public R deleteVideo(@PathVariable String videoSourceId){
        boolean flag = eduVideoUploadService.deleteVideoByVideoSourceId(videoSourceId);

        if (flag){
            return R.success().message("课程小节视频删除成功");
        }else {
            return R.error().message("课程小节视频删除失败");
        }
    }

    /**
     * 上传视频到阿里云中
     * @param file
     * @return
     */
    @ApiOperation(value = "课程小节视频上传功能测试")
    @PostMapping("videoUpload")
    public R videoUpload(@RequestParam MultipartFile file){

        String videoId = eduVideoUploadService.videoUpload(file);
        return R.success().data("videoId",videoId);
    }

    /**
     * 根据视频id获取视频播放凭证
     * @param vid
     * @return
     */
    @ApiOperation(value = "根据视频id获取视频播放凭证")
    @GetMapping("getPlayAuto/{vid}")
    public R getPlayAuto(@PathVariable String vid){

        try{
            String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
            String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
            //初始化操作
            DefaultAcsClient acsClient = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //创建获取视频播放凭证request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //传递视频id
            request.setVideoId(vid);

            //调用方法
            GetVideoPlayAuthResponse response = acsClient.getAcsResponse(request);

            //通过response获取播放凭证
            String playAuth = response.getPlayAuth();
            return R.success().data("playAuth",playAuth);
        }catch (Exception e){
            return R.error();
        }

    }

}

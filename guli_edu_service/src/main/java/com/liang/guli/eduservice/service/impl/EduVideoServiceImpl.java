package com.liang.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liang.guli.educommon.R;
import com.liang.guli.eduservice.entity.EduVideo;
import com.liang.guli.eduservice.entity.dto.VideoDto;
import com.liang.guli.eduservice.feign.EduVideoFeign;
import com.liang.guli.eduservice.mapper.EduVideoMapper;
import com.liang.guli.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-15
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private EduVideoFeign eduVideoFeign;

    /**
     * 删除课程小节
     * @param courseId
     */
    @Override
    public void removeVideoById(String courseId) {

        //批量删除云端的视频
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        //取得eduVideo的集合
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper1);
        //定义一个集合用来放videoSourceId
        List<String> list = new ArrayList<>();
        //遍历取得的eduVideo集合，取得所有的videoSourceId值
        for(int i = 0;i<eduVideos.size();i++){
            String videoSourceId = eduVideos.get(i).getVideoSourceId();
            list.add(videoSourceId);
        }
        //调用feign方法，批量删除云端的视频
        eduVideoFeign.deleteVideoByvideoSourceIdLists(list);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        int delete = baseMapper.delete(wrapper);

    }

    /**
     * 查询得到小节信息
     * @param chapterId
     * @param courseId
     * @return
     */
    @Override
    public List<VideoDto> getEduVideoList(String chapterId, String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("chapter_id",chapterId);

        //查询符合条件的小节信息
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);

        //存放videoDto的集合
        ArrayList<VideoDto> videoDtoList = new ArrayList<>();

        //遍历小节信息
        for (EduVideo eduVideo : eduVideos) {
            VideoDto videoDto = new VideoDto();

            BeanUtils.copyProperties(eduVideo,videoDto);

            videoDtoList.add(videoDto);
        }
        return videoDtoList;
    }


    /**
     * 删除章节中小节信息
     * @param videoId
     * @return
     */
    @Override
    public boolean removeVideoByVideoId(String videoId) {

        //查询云端视频id
        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();

        //删除云端视频
        if (!StringUtils.isEmpty(videoSourceId)){
            eduVideoFeign.deleteVideo(videoSourceId);
        }
        int i = baseMapper.deleteById(videoId);
        return i > 0 ? true : false;

    }

    /**
     * 通过章节的id删除小节的信息
     * @param chapterId
     * @return
     */
    @Override
    public boolean removeVideoByChapterId(String chapterId) {
        //批量删除云端的视频
        //根据传入的chapterID查询出所有的小节信息
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id",chapterId);
        List<EduVideo> eduVideos = baseMapper.selectList(queryWrapper);

        //定义一个用于存放videosourceID的集合
        ArrayList<String> list = new ArrayList<>();

        //遍历eduvideos集合
        for (int i=0;i<eduVideos.size();i++){
            String videoSourceId = eduVideos.get(i).getVideoSourceId();
            list.add(videoSourceId);
        }

        //删除云端视频
        eduVideoFeign.deleteVideoByvideoSourceIdLists(list);


        //删除数据库中的小节信息
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int i = baseMapper.delete(wrapper);
        return i>0?true:false;
    }
}

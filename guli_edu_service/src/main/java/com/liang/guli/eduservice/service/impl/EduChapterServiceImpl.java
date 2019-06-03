package com.liang.guli.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liang.guli.eduservice.entity.EduChapter;
import com.liang.guli.eduservice.entity.EduVideo;
import com.liang.guli.eduservice.entity.dto.ChapterDto;
import com.liang.guli.eduservice.entity.dto.VideoDto;
import com.liang.guli.eduservice.handler.EduException;
import com.liang.guli.eduservice.mapper.EduChapterMapper;
import com.liang.guli.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.guli.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author liliang
 * @since 2019-05-15
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;


    /**
     * 删除课程的章节
     * @param courseId
     */
    @Override
    public void removeChapterById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        int i = baseMapper.delete(wrapper);
        if (i<0){
            throw new EduException(20001,"删除课程的章节信息失败");
        }


    }

    /**
     * 查询所有的章节及章节下的小节
     * @return
     */
    @Override
    public List<ChapterDto> getEduChapterList() {
        //查询所有的章节
        List<EduChapter> eduChapters = baseMapper.selectList(null);

        //通过调用getChapterDtoList方法获取ChapterDto风格的数据
        List<ChapterDto> chapterDtoList = this.getChapterDtoList(eduChapters);

        return chapterDtoList;
    }

    /**
     *根据课程id查询到该课程的所有章节和所有小节
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterDto> getEduChapterList(String courseId) {

        //通过courseid获取指定的章节和小节信息
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);

        //通过调用getChapterDtoList方法获取ChapterDto风格的数据
        List<ChapterDto> chapterDtoList = this.getChapterDtoList(eduChapters);

        return chapterDtoList;
    }

    /**
     * 根据chapterId删除章节信息
     * @param chapterId
     * @return
     */
    @Override
    public boolean removeChapterByChapterId(String chapterId) {

        boolean b = eduVideoService.removeVideoByChapterId(chapterId);

        int i = baseMapper.deleteById(chapterId);
        if (i>0){
            return true;
        }else {
            return false;
        }

    }

    /**
     * 根据chapterId查询章节的信息
     * @param chatperId
     * @return
     */
    @Override
    public EduChapter getChatperById(String chatperId) {
        EduChapter eduChapter = baseMapper.selectById(chatperId);
        return eduChapter;
    }

    /**
     *该方法是为了封装ChapterDto实体的一个方法
     * @param eduChapters
     * @return
     */
    public List<ChapterDto> getChapterDtoList(List<EduChapter> eduChapters){
        //定义一个集合用来存储章节信息
        ArrayList<ChapterDto> chapterDtoList = new ArrayList<>();

        //遍历整个章节的集合
        for (int i=0;i<eduChapters.size();i++){

            ChapterDto chapterDto = new ChapterDto();
            EduChapter eduChapter = eduChapters.get(i);
            BeanUtils.copyProperties(eduChapter,chapterDto);

            //查询符合章节id和课程id小节信息的集合
            List<VideoDto> videoDtoList = eduVideoService.getEduVideoList(eduChapter.getId(),eduChapter.getCourseId());

            //把小节的信息设置到对应的章节信息中
            chapterDto.setVideoDtos(videoDtoList);

            //把章节信息添加到章节的集合中
            chapterDtoList.add(chapterDto);
        }
        return chapterDtoList;
    }
}

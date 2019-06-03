package com.liang.guli.eduservice.service;

import com.liang.guli.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.guli.eduservice.entity.dto.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author liliang
 * @since 2019-05-13
 */
public interface EduSubjectService{

    List<String> uploadFile(MultipartFile file);

    List<OneSubject> getSubjectList();

    boolean deleteSubjectById(String id);

    boolean saveOneSubject(EduSubject eduSubject);

    boolean saveTwoSubject(EduSubject eduSubject);
}

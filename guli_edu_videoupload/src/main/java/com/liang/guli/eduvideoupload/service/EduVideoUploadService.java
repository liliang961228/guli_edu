package com.liang.guli.eduvideoupload.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EduVideoUploadService {
    String videoUpload(MultipartFile file);

    boolean deleteVideoByVideoSourceId(String videoSourceId);

    boolean deleteVideoByvideoSourceIdLists(List<String> videoSourceIdLists);

}

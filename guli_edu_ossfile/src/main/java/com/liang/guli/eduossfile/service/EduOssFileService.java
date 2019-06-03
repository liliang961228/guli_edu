package com.liang.guli.eduossfile.service;

import org.springframework.web.multipart.MultipartFile;

public interface EduOssFileService {
    String upload(MultipartFile file);
}

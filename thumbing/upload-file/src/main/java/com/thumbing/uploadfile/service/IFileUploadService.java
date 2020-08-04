package com.thumbing.uploadfile.service;

import com.aliyun.oss.model.OSSObjectSummary;
import com.thumbing.uploadfile.dto.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/8/4 20:59
 */
public interface IFileUploadService {
    /**
     * @desc 文件上传到oss
     * @return FileUploadResult
     * @Param uploadFile
     */
    FileUploadResult upload(MultipartFile uploadFile);
    /**
     * @desc 查询oss上的所有文件
     * @return List<OSSObjectSummary>
     * @Param
     */
    List<OSSObjectSummary> list();
    /**
     * @return FileUploadResult
     * @desc 根据文件名删除oss上的文件
     * @Param objectName
     */
    FileUploadResult delete(String objectName);
    /**
     * @desc 根据文件名下载oss上的文件
     * @return
     * @Param objectName
     */
    void exportOssFile(OutputStream os, String objectName) throws IOException;
}

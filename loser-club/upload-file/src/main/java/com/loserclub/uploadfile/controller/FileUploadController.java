package com.loserclub.uploadfile.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.loserclub.shared.annotation.Authorize;
import com.loserclub.shared.auth.permission.PermissionConstants;
import com.loserclub.shared.controller.LoserClubBaseController;
import com.loserclub.uploadfile.dto.FileUploadResult;
import com.loserclub.uploadfile.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/11 14:27
 */
@Api(tags = "上传文件")
@RestController
@RequestMapping(value = "/oss")
public class FileUploadController extends LoserClubBaseController {
    @Autowired
    private FileUploadService fileUploadService;

    /**
     * @desc 文件上传到oss
     * @return FileUploadResult
     * @Param uploadFile
     */
    @ApiImplicitParams(
            @ApiImplicitParam(name = "uploadFile", paramType = "org.springframework.web.multipart.MultipartFile")
    )
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return this.fileUploadService.upload(uploadFile);
    }

    /**
     * @return FileUploadResult
     * @desc 根据文件名删除oss上的文件
     * @Param objectName
     */
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        return this.fileUploadService.delete(objectName);
    }

    /**
     * @desc 查询oss上的所有文件
     * @return List<OSSObjectSummary>
     * @Param
     */
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OSSObjectSummary> list() throws Exception {
        return this.fileUploadService.list();
    }

    /**
     * @desc 根据文件名下载oss上的文件
     * @return
     * @Param objectName
     */
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }
}

package com.thumbing.uploadfile.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.qiniu.storage.model.DefaultPutRet;
import com.thumbing.shared.annotation.Authorize;
import com.thumbing.shared.annotation.EnableResponseAdvice;
import com.thumbing.shared.auth.permission.PermissionConstants;
import com.thumbing.shared.controller.ThumbingBaseController;
import com.thumbing.uploadfile.service.impl.QiNiuService;
import com.thumbing.uploadfile.dto.FileUploadResult;
import com.thumbing.uploadfile.service.IFileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Stan Sai
 * @Date: 2020/7/11 14:27
 */
@EnableResponseAdvice
@Api(tags = "上传文件")
@RestController
@RequestMapping
public class FileUploadController extends ThumbingBaseController {
    @Autowired
    private IFileUploadService fileUploadService;
    @Autowired
    private QiNiuService qiNiuService;

    @ApiOperation("文件上传到oss")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile) {
        return this.fileUploadService.upload(uploadFile);
    }

    @ApiOperation("根据文件名删除oss上的文件")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        return this.fileUploadService.delete(objectName);
    }

    @ApiOperation("查询oss上的所有文件")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<OSSObjectSummary> list() throws Exception {
        return this.fileUploadService.list();
    }

    @ApiOperation("根据文件名下载oss上的文件")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }

    @ApiOperation("获取token")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    public String uploadQi() {
        return qiNiuService.createToken();
    }

    @ApiOperation("文件上传到七牛云")
    @Authorize(PermissionConstants.ACCESS)
    @RequestMapping(value = "/uploadQi", method = RequestMethod.POST)
    public DefaultPutRet uploadQi(@RequestParam("file") MultipartFile uploadFile, String token) throws IOException {
        return qiNiuService.upload(token, uploadFile);
    }
}

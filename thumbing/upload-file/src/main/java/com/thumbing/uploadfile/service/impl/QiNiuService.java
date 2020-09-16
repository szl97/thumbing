package com.thumbing.uploadfile.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: Stan Sai
 * @Date: 2020/9/16 9:21
 */
@Component
public class QiNiuService {
    @Value("${qiniuyun.accessKey}")
    private String accessKey;
    @Value("${qiniuyun.secretKey}")
    private String secretKey;
    @Value("${qiniuyun.bucket}")
    private String bucket;
    @Value("${qiniuyun.pipeline}")
    private String pipeline;
    private String key = null;
    @Autowired
    private ObjectMapper objectMapper;

    public String createToken(){
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap policy = new StringMap();
        policy.put("persistentPipeline", pipeline);
        long expireTime = 3600;
        String upToken = auth.uploadToken(bucket, key, expireTime, policy);
        return upToken;
    }

    public DefaultPutRet upload(String token, MultipartFile uploadFile) throws IOException {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        Response response = uploadManager.put(uploadFile.getBytes(),key,token);
        return objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
    }
}

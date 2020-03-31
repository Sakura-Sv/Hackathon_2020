package com.testdb.demo.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QiniuUtil {

    /**
     * 七牛云工具类
     */

    static String ACCESS_KEY = "bauMxdmpiAfyHKZAPW5nsT7pwI0PSRLnDLpaKRgP";
    static String SECRET_KEY = "lgQHDim9JWvhZUJDU0Od2I-WObd1IGCAmJLIeI8N";
    static String bucketName = "test-lz";

    String key = "";
    String FilePath = "";

    static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    static String uploadToken = auth.uploadToken(bucketName);

    // 华东仓库
    Configuration cfg = new Configuration(Region.region0());
    UploadManager uploadManager = new UploadManager(cfg);

    // 获取上传令牌
    @SneakyThrows
    public static String getToken(){
        System.out.println(bucketName);
        return auth.uploadToken(bucketName);
    }

    // 应该用不到这个
    public void upload(String filePath) throws IOException {
        try {
            Response response = uploadManager.put(FilePath, key, uploadToken);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

}
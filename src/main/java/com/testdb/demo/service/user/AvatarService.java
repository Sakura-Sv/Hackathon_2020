package com.testdb.demo.service.user;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiniu.util.StringMap;
import com.testdb.demo.entity.user.Avatar;
import com.testdb.demo.entity.QiniuCallbackMessage;
import com.testdb.demo.mapper.user.AvatarMapper;
import com.testdb.demo.utils.QiniuUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AvatarService extends ServiceImpl<AvatarMapper, Avatar> {

    @SneakyThrows
    public void uploadAvatarTest(String username){
        /**
         * 测试用
         */
        StringMap policy = new StringMap();
        policy.put("callbackUrl", "http://9fpht5.natappfree.cc/api/callback/avatar");
        policy.put("callbackBody",
                "{\"username\":" + "\""+username+"\"," + "\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"}");
        policy.put("callbackBodyType", "application/json");
        String sourceUrl = "D:\\OneDrive\\桌面\\23333.jpg";
        String targetUrl = "avatar/" + username;
        String token = QiniuUtil.getTokenWithPolicy(targetUrl, policy);
        QiniuUtil.uploadTest(sourceUrl, targetUrl, token);
    }

    @SneakyThrows
    public String uploadAvatar(String username){
        StringMap policy = new StringMap();
        policy.put("callbackUrl", "https://thenebula.cn/api/callback/avatar");
        policy.put("callbackBody",
                "{\"username\":" + "\""+username+"\"," + "\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\"}");
        policy.put("callbackBodyType", "application/json");
        String targetUrl = "avatar/" + username;
        String token = QiniuUtil.getTokenWithPolicy(targetUrl, policy);
        return token;
    }

    @SneakyThrows
    public void uploadAvatarCallback(HttpServletRequest request){
        byte[] callbackBody = new byte[2048];
        request.getInputStream().read(callbackBody);
        QiniuCallbackMessage message = JSON.parseObject(callbackBody, QiniuCallbackMessage.class);
        String username = message.getUsername();
        if(QiniuUtil.validateCallback(request, callbackBody)){
            String newUrl = "http://q81okm9pv.bkt.clouddn.com/avatar/"+username;
            this.update(new UpdateWrapper<Avatar>().eq("username", username)
                    .set("url", newUrl));
        }
    }

}

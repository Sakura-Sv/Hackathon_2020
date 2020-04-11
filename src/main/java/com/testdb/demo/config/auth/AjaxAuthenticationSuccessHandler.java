package com.testdb.demo.config.auth;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.testdb.demo.entity.user.User;
import com.testdb.demo.service.user.UserService;
import com.testdb.demo.utils.JwtTokenUtil;
import com.testdb.demo.utils.response.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserService us;

    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)  {

        User userDetails = (User) authentication.getPrincipal();

        String jwtToken = JwtTokenUtil.generateToken(userDetails.getUsername(), 3600*24*7, "_secret");
        Map<String, String> token = new HashMap<String, String>();
        if(userDetails.getBeRefresh()){
            us.update(new UpdateWrapper<User>()
                    .eq("username", userDetails.getUsername())
                    .set("be_refresh", 0));
        }
        token.put("token", jwtToken);

        httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.success(token)));

    }
}

package com.testdb.demo.config.auth;

import com.alibaba.fastjson.JSON;
import com.testdb.demo.entity.User;
import com.testdb.demo.service.CustomUserDetailsService;
import com.testdb.demo.utils.AjaxResponseBody;
import com.testdb.demo.utils.JwtTokenUtil;
import com.testdb.demo.utils.Result;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    @SneakyThrows
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)  {

        User userDetails = (User) authentication.getPrincipal();

        String jwtToken = JwtTokenUtil.generateToken(userDetails.getUsername(), 3600*24*7, "_secret");
        Map<String, String> token = new HashMap<String, String>();
        token.put("token", jwtToken);

        httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.success(token)));

    }
}

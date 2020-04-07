package com.testdb.demo.config.auth;

import com.alibaba.fastjson.JSON;
import com.testdb.demo.utils.Result;
import com.testdb.demo.utils.ResultStatus;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    @SneakyThrows
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e){

        httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON.toString());
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.failure(ResultStatus.FORBIDDEN
        .setMessage("您的权限不够或者您的密码已被修改"))));
    }

}

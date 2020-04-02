//package com.testdb.demo.interceptor;
//
//import org.springframework.lang.Nullable;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class TestInterceptor implements HandlerInterceptor {
//
//    @Override
//    public void postHandle(HttpServletRequest request,
//                            HttpServletResponse response,
//                            Object handler, @Nullable ModelAndView modelAndView)
//            throws Exception {
//        response.setHeader("Content-Type","application/json;charset=UTF-8");
//    }
//
//}

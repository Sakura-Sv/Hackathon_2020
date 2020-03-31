//package com.testdb.demo.config;
//
//import com.testdb.demo.interceptor.TestInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class WebMvcConfig extends WebMvcConfigurationSupport {
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
//        registry.addInterceptor(new TestInterceptor())
//                .addPathPatterns("/**")
//                .order(0);
//        super.addInterceptors(registry);
//    }
//}

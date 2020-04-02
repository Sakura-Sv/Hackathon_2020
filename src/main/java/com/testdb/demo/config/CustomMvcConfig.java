//package com.testdb.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
////@Configuration
////public class CustomMvcConfig implements WebMvcConfigurer {
////
////    @Bean
////    public HttpMessageConverter<String> responseBodyStringConverter() {
////        StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
////        return converter;
////    }
////
////    @Override
////    public void configureMessageConverters(List<HttpMessageConverter<?>> converters){
////        converters.add(responseBodyStringConverter());
////    }
////
////}
//
//@Configuration
//public class CustomMvcConfig extends WebMvcConfigurationSupport {
//
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    }
//
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(responseBodyConverter());
//        addDefaultHttpMessageConverters(converters);
//    }
//
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer.favorPathExtension(false);
//    }
//
//}
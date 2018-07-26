package com.jbwl.web.systemconfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @Author: jipeng
 * @Description:
 * @Date: Created in 2018/6/22 9:50
 */
@Configuration
public class WebFilter {

    /**
     * 字符集过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean encodingFilter(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8",true,true);
        registration.setFilter(characterEncodingFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }




}

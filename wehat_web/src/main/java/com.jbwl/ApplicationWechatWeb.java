package com.jbwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;

/**
 * @Author: jipeng
 * @Description: web项目启动类
 * @Date: Created in 2018/6/20 7:47
 */

@SpringBootApplication
@EnableAutoConfiguration
public class ApplicationWechatWeb {

    public static void main(String[] arg){
        SpringApplication app = new SpringApplication(ApplicationWechatWeb.class);


        app.run();
    }







}

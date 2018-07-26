package com.jbwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: jipeng
 * @Description: web项目启动类
 * @Date: Created in 2018/6/20 7:47
 */

@SpringBootApplication
public class ApplicationWechatApi {

    public static void main(String[] arg){
        SpringApplication app = new SpringApplication(ApplicationWechatApi.class);


        app.run();
    }


}

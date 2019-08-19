package com.dh.course.sentinel;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 压测参数如下，使用ab
 *  ab -n 500 -c 100 http://localhost:7070/sentinel/say/hellowerwer
 */
@SpringBootApplication
@EnableDubbo
@PropertySource(value = "classpath:/dubbo-config.properties")
public class SentinelWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelWebApplication.class);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

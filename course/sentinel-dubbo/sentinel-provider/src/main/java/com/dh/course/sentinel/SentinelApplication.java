package com.dh.course.sentinel;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

@EnableDubbo(scanBasePackages = "com.dh.course.sentinel")
@PropertySource(value = "classpath:/dubbo-config.properties")
public class SentinelApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SentinelApplication.class);
        context.refresh();
        System.out.println("provider is starting...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

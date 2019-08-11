package com.dh.course.sentinel;


import com.alibaba.dubbo.config.annotation.Service;

@Service
public class SentinelServiceImpl implements SentinelService {

    @Override
    public String sayHello(String txt) {
        System.out.println("recive:" + txt);
        return "Hello world: " + txt;
    }
}

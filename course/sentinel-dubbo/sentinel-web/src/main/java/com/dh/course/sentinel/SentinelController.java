package com.dh.course.sentinel;


import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("sentinel")
public class SentinelController {

    @Reference
    private SentinelService sentinelService;

    @GetMapping("say/{txt}")
    public String say(@PathVariable("txt") String txt) {
        return sentinelService.sayHello(txt);
    }

}

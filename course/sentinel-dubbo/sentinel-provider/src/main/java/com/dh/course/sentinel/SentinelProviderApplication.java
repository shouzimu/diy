package com.dh.course.sentinel;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 启动参数
 * -Dproject.name=sentinel-dubbo -Dcsp.sentinel.dashboard.server=127.0.0.1:8080 -Dcsp.sentinel.log.use.pid=true
 */
@EnableDubbo(scanBasePackages = "com.dh.course.sentinel")
@PropertySource(value = "classpath:/dubbo-config.properties")
@SpringBootApplication
public class SentinelProviderApplication {

    public static void main(String[] args) {
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
        SpringApplication.run(SentinelProviderApplication.class);
    }
}

package com.dh.course.sentinel.cluster;

import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import java.util.Collections;

/**
 * 启动参数
 * -Dproject.name=sentinel-dubbo -Dcsp.sentinel.dashboard.server=127.0.0.1:8080 -Dcsp.sentinel.log.use.pid=true
 *
 *
 * nacos配置如下
 * [
 *     {
 *         "resource":"com.dh.course.sentinel.SentinelService:sayHello(java.lang.String)",
 *         "grade":1,
 *         "count":5,
 *         "clusterMode":true,
 *         "clusterConfig":{
 *             "flowId":111111,
 *             "thresholdType":1,
 *             "fallbackToLocalWhenFail":true
 *         }
 *     }
 * ]
 */
public class ClusterDemoApplication {

    public static void main(String[] args) throws Exception {
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();
        ClusterServerConfigManager.loadGlobalTransportConfig(
                new ServerTransportConfig().setIdleSeconds(600).setPort(9999));
        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton("sentinel-dubbo")); //设置成动态
        tokenServer.start();
    }
}

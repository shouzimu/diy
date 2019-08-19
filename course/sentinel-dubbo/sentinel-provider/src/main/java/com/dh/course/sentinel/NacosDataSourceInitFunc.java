package com.dh.course.sentinel;

import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.List;

public class NacosDataSourceInitFunc implements InitFunc {

    private final String CLUSTER_SERVER_HOST = "localhost"; //token-server的地址
    private final int CLUSTER_SERVER_PORT = 9999;
    private final int REQUEST_TIME_OUT = 200000; //请求超时时间

    private final String APP_NAME = "sentinel-dubbo"; //namespace

    //nacos的配置()
    private final String remoteAddress = "localhost"; //nacos 配置中心的服务host
    private final String groupId = "SENTINEL_GROUP";
    private final String FLOW_POSTFIX = "-flow-rules"; //dataid（names+postfix）


    @Override
    public void init() throws Exception {
        loadClusterClientConfig();
        registryClusterFlowRuleProperty();
    }

    private void loadClusterClientConfig() {
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost(CLUSTER_SERVER_HOST);
        assignConfig.setServerPort(CLUSTER_SERVER_PORT);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clusterClientConfig = new ClusterClientConfig();
        clusterClientConfig.setRequestTimeout(REQUEST_TIME_OUT);
        ClusterClientConfigManager.applyNewConfig(clusterClientConfig);
    }


    //注册动态数据源
    private void registryClusterFlowRuleProperty() {
        ReadableDataSource<String, List<FlowRule>> rds =
                new NacosDataSource<>(remoteAddress, groupId, APP_NAME + FLOW_POSTFIX,
                        source -> JSON.parseObject(source, new TypeReference<>() {
                        }));
        FlowRuleManager.register2Property(rds.getProperty());
    }
}

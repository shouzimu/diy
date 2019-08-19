package com.dh.course.sentinel.cluster;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.List;

public class DemoClusterInitFunc implements InitFunc {

    private final String remoteAddress = "127.0.0.1"; //nacos 配置中心的服务host
    private final String groupId = "SENTINEL_GROUP";
    private final String FLOW_POSTFIX = "-flow-rules"; //dataid（names+postfix）

    //意味着当前的token-server会从nacos上获得限流的规则
    @Override
    public void init() throws Exception {
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> rds =
                    new NacosDataSource<>(remoteAddress, groupId, namespace + FLOW_POSTFIX,
                            source -> JSON.parseObject(source, new TypeReference<>() {
                            }));
            return rds.getProperty();
        });
    }
}

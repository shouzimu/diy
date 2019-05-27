package com.dh.dubbo.registry;

import com.dh.dubbo.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ZookeeperRegistry implements RegistryService {

    private CuratorFramework curatorFramework;


    public ZookeeperRegistry() {
        curatorFramework = CuratorFrameworkFactory.builder().sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).connectString(ZkConfig.SERVER_URL)
                .build();
        curatorFramework.start();
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        String servicePath = ZkConfig.REGISTER_PATH + "/" + serviceName;
        try {
            if (curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath, "0".getBytes());
            }
            String addressPath = servicePath + "/" + serviceAddress;
            String node = curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath, "0".getBytes());
            System.out.println("服务注册成功" + node);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

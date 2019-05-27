package com.dh.dubbo.discovery;

import com.dh.dubbo.ZkConfig;
import java.util.ArrayList;
import java.util.List;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 * @date 2019-05-25 21:49
 */
public class ServerDiscovery implements Discovery {

    private CuratorFramework curatorFramework;

    List<String> repos = new ArrayList<>();

    public ServerDiscovery() {
        curatorFramework = CuratorFrameworkFactory.builder().sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).connectString(ZkConfig.SERVER_URL)
                .build();
        curatorFramework.start();
    }

    public String discovery(String serviceName) {
        String path = ZkConfig.REGISTER_PATH + "/" + serviceName;
        try {
            repos = curatorFramework.getChildren().forPath(path);
            if (repos.size() > 0) {
                return repos.get(0);
            }
            //利用zk的心跳机制，监听某个节点下面的节点变化
            registerWatch(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void registerWatch(String path) {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);

        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent)
                    throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

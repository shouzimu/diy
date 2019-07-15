package com.dh.course.zk;

import java.util.concurrent.CountDownLatch;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class TestZK {

    static String zookeeperConnectionString = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    static RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);

    static CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperConnectionString, policy);

    static {
        curatorFramework.start();
    }

    public static void testAdd() throws Exception {
        try {
            String s = curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/dd4");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        testWatch();
    }

    public static void testWatch() throws Exception {
        final NodeCache nodeCache = new NodeCache(curatorFramework, "/dd", false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(
                () -> System.out.println("node data update, new data: " + new String(nodeCache.getCurrentData().getData())));

        Thread.currentThread().join();
    }
}

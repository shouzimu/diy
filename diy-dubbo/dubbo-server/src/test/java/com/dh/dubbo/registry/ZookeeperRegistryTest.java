package com.dh.dubbo.registry;

import org.testng.annotations.Test;

public class ZookeeperRegistryTest {

    @Test
    public void testRegister() {
        ZookeeperRegistry registry = new ZookeeperRegistry();
        registry.register("com.dh.xxoo", "127.0.0.1:8080");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

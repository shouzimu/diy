package com.dh.service;

import com.dh.dubbo.registry.RegistryService;
import com.dh.dubbo.registry.ZookeeperRegistry;
import com.dh.dubbo.rpc.NettyServer;
import java.io.IOException;

public class ProviderTest {

    public static void main(String[] args) throws IOException {
        DemoService demoService = new DemoServiceImpl();
        NettyServer server = new NettyServer();
        server.setServiceAddress("127.0.0.1:9090");
        RegistryService registryService = new ZookeeperRegistry();
        server.setRegistryService(registryService);
        server.bindService(demoService);
        server.publisher();
        System.in.read();

    }
}

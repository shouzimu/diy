package com.dh.service;

import com.dh.dubbo.discovery.RpcClientProxy;
import com.dh.dubbo.discovery.ServerDiscovery;

public class ConsumerTest {

    public static void main(String[] args) {
        ServerDiscovery discovery = new ServerDiscovery();
        RpcClientProxy proxy = new RpcClientProxy(discovery);
        DemoService demoService = proxy.create(DemoService.class);
        String str = demoService.sayHello("helloWorld");
        System.out.println("rps result : " + str);

    }
}

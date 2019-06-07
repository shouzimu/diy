package com.dh.rpc.consumer;

import com.dh.rpc.service.IHelloService;

public class ConsumerApp {

    public static void main(String[] args) {
        try {
            RpcProxyClient rpcProxyClient = new RpcProxyClient();
            IHelloService helloService = (IHelloService) rpcProxyClient.create(IHelloService.class);
            String res = helloService.sayHello(" china");
            System.out.println("res: " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

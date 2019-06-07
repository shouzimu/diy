package com.dh.rpc.provider;

import com.dh.rpc.service.HelloService;
import com.dh.rpc.service.IHelloService;

public class ProviderApp {

    public static void main(String[] args) {
        RpcProxyServer server = new RpcProxyServer();
        IHelloService helloService = new HelloService();
        server.publisher(helloService);
        server.start();
    }
}

package com.dh.rpc.service;

public class HelloService implements IHelloService {

    @Override
    public String sayHello(String name) {
        return "Hello: " + name;
    }
}

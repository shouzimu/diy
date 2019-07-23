package com.dh.dubbo;

public class PayServiceImpl implements PayService {

    @Override
    public String sayHello(String name) {
        return "hello: " + name;
    }
}

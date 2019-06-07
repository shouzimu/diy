package com.dh.rpc.consumer;

import com.dh.rpc.common.RpcRequest;
import java.lang.reflect.Proxy;

public class RpcProxyClient<T> {

    RpcNetTransport transport;

    public <T> T create(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                (proxy, method, args) -> {
                    RpcRequest request = new RpcRequest();
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setTypes(method.getParameterTypes());
                    request.setParams(args);
                    return transport.send(request);
                });
    }

    public RpcProxyClient() {
        this.transport = new RpcNetTransport();
    }
}

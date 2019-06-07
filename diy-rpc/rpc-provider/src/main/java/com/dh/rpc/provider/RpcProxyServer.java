package com.dh.rpc.provider;

import java.io.IOException;
import java.lang.reflect.AnnotatedType;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProxyServer {

    ExecutorService executorService = Executors.newFixedThreadPool(5);

    private Map<String, Object> serviceMap = new HashMap<>();


    public void publisher(Object service) {
        Class<?> zlass = service.getClass();
        AnnotatedType[] types = zlass.getAnnotatedInterfaces();
        AnnotatedType Interface = types[0];
        String interfaceName = Interface.getType().getTypeName();
        serviceMap.put(interfaceName, service);
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8080));
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessHandler(socket, serviceMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

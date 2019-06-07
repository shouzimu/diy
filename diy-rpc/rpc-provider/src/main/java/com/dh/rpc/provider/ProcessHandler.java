package com.dh.rpc.provider;

import com.dh.rpc.common.RpcRequest;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessHandler implements Runnable {

    private Socket socket;

    private Map<String, Object> serviceMap;

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) inputStream.readObject();
            Object res = invoke(request);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(res);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object invoke(RpcRequest request) throws Exception {
        String className = request.getClassName();
        String methodName = request.getMethodName();
        Object[] params = request.getParams();
        Class[] methodClass = request.getTypes();

        String version = request.getVersion();

        if (null != version) {
            className = className + version;
        }
        Object serverClass = serviceMap.get(className);
        Method method = serverClass.getClass().getMethod(methodName, methodClass);
        return method.invoke(serverClass, params);
    }

    public ProcessHandler(Socket socket, Map<String, Object> serviceMap) {
        this.socket = socket;
        this.serviceMap = serviceMap;
    }
}

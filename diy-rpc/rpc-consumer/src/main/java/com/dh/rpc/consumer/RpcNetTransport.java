package com.dh.rpc.consumer;

import com.dh.rpc.common.RpcRequest;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcNetTransport {


    public Object send(RpcRequest request) {
        Object res = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            res = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}

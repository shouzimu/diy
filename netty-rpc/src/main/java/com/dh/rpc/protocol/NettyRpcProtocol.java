package com.dh.rpc.protocol;

import lombok.Data;

@Data
public class NettyRpcProtocol {

    private static final long serialVersionUID = 1L;

    private String className;

    private String methodName;

    private Object[] params;

    private Class<?>[] types;

}

package com.dh.dubbo;

import java.io.Serializable;
import lombok.Data;

/**
 * rpc请求
 *
 * @date 2019-05-23 22:04
 */
@Data
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String className;

    private String methodName;

    private Object[] params;

    private Class<?>[] types;
}

package com.dh.dubbo.registry;

/**
 * @date 2019-05-22 21:39
 */
public interface RegistryService {

    void register(String serviceName, String serviceAddress);
}

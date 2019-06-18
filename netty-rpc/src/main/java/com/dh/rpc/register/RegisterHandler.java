package com.dh.rpc.register;

import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RegisterHandler extends ChannelInboundHandlerAdapter {
    //1、根据一个包名将所有符合条件的class全部扫描出来，放到一个容器中

    //2、给每一个对应的class起一个唯一的名字，作为服务名称，保存到容器中

    //3、


    private Set<String> classNames;
    Map<String, Object> serviceMap;

    private void doRegistry() {
        if (null == classNames) {
            return;
        }

        serviceMap = new HashMap<>(classNames.size());

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> i = clazz.getInterfaces()[0];

                serviceMap.put(i.getName(), clazz.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }


    public void scannerClass(String packageName) {
        if (null == packageName) {
            throw new RuntimeException("包不能为空");
        }
        String path = packageName.replaceAll("\\.", "/");
        URL url = this.getClass().getClassLoader().getResource(path);

        File classPathFile = new File(url.getFile());
        for (File file : classPathFile.listFiles()) {
            if (file.isDirectory()) {
                scannerClass(packageName + "." + file.getName());
            } else {
                classNames.add(packageName + "." + file.getName().replace(".class", ""));
            }
        }
    }

}

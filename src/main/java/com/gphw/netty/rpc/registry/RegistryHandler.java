package com.gphw.netty.rpc.registry;

import com.gphw.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Jiang
 * @Date: 2019/6/18 21:54
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {


    private static ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<String, Object>();

    private List<String> classNames = new ArrayList<String>();

    public RegistryHandler() {
        scannerClass("com.gphw.netty.rpc.provider");
        doRegistry();
    }

    /**
     * 完成注册
     */
    private void doRegistry() {
        if (classNames.size() == 0) {
            return;
        }
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                Class<?> i = clazz.getInterfaces()[0];
                registryMap.put(i.getName(), clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归扫描
     *
     * @param packageName
     */
    private void scannerClass(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scannerClass(packageName + "." + file.getName());
            } else {
                classNames.add(packageName + "." + file.getName().replace(".class", "").trim());
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        Object result = new Object();
        InvokerProtocol protocol = (InvokerProtocol) msg;
        String className = protocol.getClassName();
        if (registryMap.containsKey(className)) {
            Object clazz = registryMap.get(className);
            Method method = clazz.getClass().getMethod(protocol.getMethodName(), protocol.getParams());
            result =method.invoke(clazz, protocol.getValues());
        }
        channelHandlerContext.write(result);
        channelHandlerContext.flush();
        channelHandlerContext.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }
}

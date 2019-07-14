package com.gphw.netty.rpc.registry;

import com.gphw.netty.rpc.protocol.InvokerProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Jiang
 * @Date: 2019/6/18 21:54
 */
public class RegistryHandler extends ChannelInboundHandlerAdapter {

    //注册中心
    private IRegistryCenter registryCenter=new RegistryCenterWithZk();

    private  ConcurrentHashMap<String, Object> registryMap;


    public RegistryHandler(ConcurrentHashMap<String, Object> registryMap){
        this.registryMap=registryMap;
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
        System.out.println("端口8088返回结果："+result);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        throwable.printStackTrace();
        channelHandlerContext.close();
    }


}

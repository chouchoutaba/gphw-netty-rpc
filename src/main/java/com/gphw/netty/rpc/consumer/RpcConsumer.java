package com.gphw.netty.rpc.consumer;

import com.gphw.netty.rpc.api.IRpcCalCulateService;
import com.gphw.netty.rpc.api.IRpcHelloService;
import com.gphw.netty.rpc.consumer.proxy.RpcProxy;

/**
 * @Author: Jiang
 * @Date: 2019/6/21 21:09
 */
public class RpcConsumer {

    public static void main(String[] args) {
        IRpcHelloService rpcHello = RpcProxy.create(IRpcHelloService.class);
        String helloResult = rpcHello.hello("Jiang");
        System.out.println(helloResult);

        IRpcCalCulateService service = RpcProxy.create(IRpcCalCulateService.class);

        System.out.println("10 + 5 = " + service.add(10, 5));
        System.out.println("10 ÷ 5 = " + service.divide(10, 5));
    }

}

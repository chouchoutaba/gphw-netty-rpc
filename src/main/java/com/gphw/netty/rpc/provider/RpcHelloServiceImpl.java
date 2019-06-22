package com.gphw.netty.rpc.provider;

import com.gphw.netty.rpc.api.IRpcHelloService;

/**
 *  @Author: Jiang
 *  @Date: 2019/6/18 21:09
 */
public class RpcHelloServiceImpl implements IRpcHelloService {
    @Override
    public String hello(String name) {
        return "Hello! "+name;
    }
}

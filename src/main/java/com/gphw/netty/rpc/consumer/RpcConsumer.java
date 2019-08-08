package com.gphw.netty.rpc.consumer;

import com.gphw.netty.rpc.api.IRpcCalCulateService;
import com.gphw.netty.rpc.consumer.proxy.RpcProxy;

/**
 * @Author: Jiang
 * @Date: 2019/6/21 21:09
 */
public class RpcConsumer {

    public static void main(String[] args) {
        IRpcCalCulateService service = null;
        try {
            System.out.println("分支代码");
            service = RpcProxy.create(IRpcCalCulateService.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        do {
            try {
                System.out.println("10 + 5 = " + service.add(10, 5));
                i++;
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (i < 100);

    }
}



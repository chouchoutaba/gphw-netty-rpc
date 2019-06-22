package com.gphw.netty.rpc.provider;

import com.gphw.netty.rpc.api.IRpcCalCulateService;

public class RpcCalCulateServiceImpl implements IRpcCalCulateService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int substract(int a, int b) {
        return a - b;
    }

    @Override
    public int multiply(int a, int b) {
        return a * b;
    }

    @Override
    public int divide(int a, int b) {
        if (b != 0) {
            return a / b;
        }
        return 0;
    }
}

package com.gphw.netty.rpc.discovery;

import java.util.List;

public interface LoadBalanceStrategy {

    String selectHost(List<String> repos);

}

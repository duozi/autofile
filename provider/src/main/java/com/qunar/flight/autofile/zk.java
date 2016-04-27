package com.qunar.flight.autofile;

import qunar.tc.common.zookeeper.ZKClient;
import qunar.tc.common.zookeeper.ZKClientCache;

import java.util.List;

/**
 * Created by zhouxi.zhou on 2016/4/20.
 */
public class zk {
    public static void zk() throws Exception {
        ZKClient zkClient = ZKClientCache.get("zk.beta.corp.qunar.com:2181");
        List<String> nodes = zkClient.getChildren("/ibeplus_beta/com.qunar.ibeplus.api.itf.IAirBookOrderService/providers");
        for (String node : nodes) {
//            if (node.contains("pidshare")) {
                System.out.println("+++++"+node);
//            }
        }
    }

    public static void main(String[] args) {
        try {
            zk();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

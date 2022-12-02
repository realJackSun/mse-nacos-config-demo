package com.kuae;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class NacosConfigDemo {
    
     static final String mseServerAddr = "{mse_id}-p.nacos-ans.mse.aliyuncs.com";
    static final String dataId = "mse-dataId2";
    static final String group = "mse-group3";
    static final Properties propertiesForLocal;
    static final Properties propertiesForMse;
    static final String keyId = "alias/acs/mse";
    static final String regionId = "{regionId}";
    
    // 账号的AK/SK
    static final String akForFather = "{accessKey}";
    static final String skForFather = "{secretKey}";
    static {
        propertiesForLocal = new Properties();
        propertiesForMse = new Properties();
        // 给访问MSE的授予基础信息
        propertiesForMse.put("serverAddr", mseServerAddr);
        propertiesForMse.put("keyId", keyId);
        propertiesForMse.put("regionId", regionId);
        propertiesForMse.put("namespace", "{namespaceId}");
        // 给访问MSE的Properties授予AK/SK
        propertiesForMse.put("accessKey", akForFather);
        propertiesForMse.put("secretKey", skForFather);
    }
    
    public static void main(String[] args) throws NacosException, InterruptedException {
        testLogic();
    }
    
    public static void testLogic() throws NacosException, InterruptedException {
        ConfigService configService = NacosFactory.createConfigService(propertiesForMse);
        String content = "kuae-test-content1111";
        for (int i = 0; i < 1; i++) {
            TimeUnit.MILLISECONDS.sleep(10);
            boolean result = configService.publishConfig(dataId, group, content + i);
            System.out.println("*****" + result);
        }
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }
    
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println(configInfo);
            }
        });
        Thread.sleep(999999999L);
    }
}
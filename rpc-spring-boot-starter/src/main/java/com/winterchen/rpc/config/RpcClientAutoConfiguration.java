package com.winterchen.rpc.config;

import com.winterchen.core.balancer.FullRoundBalance;
import com.winterchen.core.balancer.LoadBalance;
import com.winterchen.core.balancer.RandomBalance;
import com.winterchen.core.discovery.DiscoveryService;
import com.winterchen.core.discovery.NacosDiscoveryService;
import com.winterchen.core.discovery.ZookeeperDiscoveryService;
import com.winterchen.rpc.processor.RpcClientProcessor;
import com.winterchen.rpc.proxy.ClientStubProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/17 10:13
 * @description 自动配置
 **/
@Configuration
@ConditionalOnProperty("rpc.client.discovery-addr")
public class RpcClientAutoConfiguration {

    @Bean
    public RpcClientProperties ppcClientProperties(Environment environment) {
        //这种初始化方式是因为收到下面processor的依赖导致该配置在比较早的时期进行初始化，如果使用普通的方式会无法解析到配置文件
        BindResult<RpcClientProperties> result = Binder.get(environment)
                .bind("rpc.client", RpcClientProperties.class);
        return result.get();
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientStubProxyFactory clientStubProxyFactory() {
        return new ClientStubProxyFactory();
    }


    @Primary
    @Bean(name = "loadBalance")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "balance", havingValue = "randomBalance", matchIfMissing = true)
    public LoadBalance randomBalance() {
        return new RandomBalance();
    }

    @Bean(name = "loadBalance")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "rpc.client", name = "balance", havingValue = "fullRoundBalance")
    public LoadBalance loadBalance() {
        return new FullRoundBalance();
    }

    @Primary
    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "type", havingValue = "zookeeper", matchIfMissing = true)
    @ConditionalOnMissingBean
    @ConditionalOnBean({RpcClientProperties.class, LoadBalance.class})
    public DiscoveryService discoveryService(@Autowired RpcClientProperties properties,
                                             @Autowired LoadBalance loadBalance) {
        return new ZookeeperDiscoveryService(properties.getDiscoveryAddr(), loadBalance);
    }

    @Bean
    @ConditionalOnProperty(prefix = "rpc.client", name = "type", havingValue = "nacos")
    @ConditionalOnMissingBean
    @ConditionalOnBean({RpcClientProperties.class})
    public DiscoveryService nacosDiscoveryService(@Autowired RpcClientProperties rpcClientProperties) {
        return new NacosDiscoveryService(rpcClientProperties.getDiscoveryAddr());
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcClientProcessor rpcClientProcessor(@Autowired ClientStubProxyFactory clientStubProxyFactory,
                                                 @Autowired DiscoveryService discoveryService,
                                                 @Autowired RpcClientProperties properties) {
        return new RpcClientProcessor(clientStubProxyFactory, discoveryService, properties);
    }



}
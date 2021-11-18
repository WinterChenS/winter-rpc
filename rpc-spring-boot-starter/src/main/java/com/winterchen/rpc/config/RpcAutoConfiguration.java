package com.winterchen.rpc.config;

import com.winterchen.core.balancer.FullRoundBalance;
import com.winterchen.core.balancer.LoadBalance;
import com.winterchen.core.balancer.RandomBalance;
import com.winterchen.core.discovery.DiscoveryService;
import com.winterchen.core.discovery.ZookeeperDiscoveryService;
import com.winterchen.core.register.RegistryService;
import com.winterchen.core.register.ZookeeperRegistryService;
import com.winterchen.rpc.processor.RpcClientProcessor;
import com.winterchen.rpc.provider.RpcServerProvider;
import com.winterchen.rpc.proxy.ClientStubProxyFactory;
import com.winterchen.rpc.transport.server.NettyRpcServer;
import com.winterchen.rpc.transport.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/17 10:13
 * @description 自动配置
 **/
@Configuration
@EnableConfigurationProperties
public class RpcAutoConfiguration {



    @Bean
    @ConditionalOnProperty("rpc.server.registry-addr")
    @ConfigurationProperties(prefix = "rpc.server")
    public RpcServerProperties rpcServerProperties() {
        return new RpcServerProperties();
    }

    @Bean
    @ConditionalOnProperty("rpc.client.discovery-addr")
    @ConfigurationProperties(prefix = "rpc.client")
//    @ConditionalOnMissingBean
    public RpcClientProperties rpcClientProperties() {
        return new RpcClientProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({RpcServerProperties.class})
    public RegistryService registryService(@Autowired RpcServerProperties rpcServerProperties) {
        return new ZookeeperRegistryService(rpcServerProperties.getRegistryAddr());
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


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({RpcClientProperties.class, LoadBalance.class})
    public DiscoveryService discoveryService(@Autowired RpcClientProperties rpcClientProperties,
                                             @Autowired LoadBalance loadBalance) {
        return new ZookeeperDiscoveryService(rpcClientProperties.getDiscoveryAddr(), loadBalance);
    }

    @Bean
    @ConditionalOnBean({RpcClientProperties.class})
    @ConditionalOnMissingBean
    public RpcClientProcessor rpcClientProcessor(@Autowired ClientStubProxyFactory clientStubProxyFactory,
                                                 @Autowired DiscoveryService discoveryService,
                                                 @Autowired RpcClientProperties rpcClientProperties) {
        return new RpcClientProcessor(clientStubProxyFactory, discoveryService, rpcClientProperties);
    }


    @Bean
    @ConditionalOnBean({RpcServerProperties.class})
    @ConditionalOnMissingBean(RpcServer.class)
    RpcServer RpcServer() {
        return new NettyRpcServer();
    }

    @Bean
    @ConditionalOnMissingBean(RpcServerProvider.class)
    @ConditionalOnBean({RpcServer.class})
    RpcServerProvider rpcServerProvider(@Autowired RegistryService registryService,
                                        @Autowired RpcServer rpcServer,
                                        @Autowired RpcServerProperties rpcServerProperties){
        return new RpcServerProvider(registryService, rpcServer, rpcServerProperties);
    }

}
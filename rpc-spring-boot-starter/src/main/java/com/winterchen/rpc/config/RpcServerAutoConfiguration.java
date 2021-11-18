package com.winterchen.rpc.config;

import com.winterchen.core.register.RegistryService;
import com.winterchen.core.register.ZookeeperRegistryService;
import com.winterchen.rpc.provider.RpcServerProvider;
import com.winterchen.rpc.transport.server.NettyRpcServer;
import com.winterchen.rpc.transport.server.RpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/18 14:01
 * @description 服务端自动配置
 **/
@Configuration
@ConditionalOnProperty("rpc.server.registry-addr")
public class RpcServerAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "rpc.server")
    public RpcServerProperties rpcServerProperties() {
        return new RpcServerProperties();
    }


    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({RpcServerProperties.class})
    public RegistryService registryService(@Autowired RpcServerProperties rpcServerProperties) {
        return new ZookeeperRegistryService(rpcServerProperties.getRegistryAddr());
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
    RpcServerProvider rpcServerProvider(@Autowired RpcServerProperties rpcServerProperties,
                                        @Autowired RegistryService registryService,
                                        @Autowired RpcServer rpcServer){
        return new RpcServerProvider(registryService, rpcServer, rpcServerProperties);
    }

}
package com.winterchen.core.balancer;

import com.winterchen.core.common.ServiceInfo;

import java.util.List;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 16:13
 * @description 负载均衡算法接口
 **/
public interface LoadBalance {


    ServiceInfo chooseOne(List<ServiceInfo> serviceInfos);

}
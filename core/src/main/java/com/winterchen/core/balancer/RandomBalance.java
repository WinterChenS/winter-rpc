package com.winterchen.core.balancer;

import com.winterchen.core.common.ServiceInfo;

import java.util.List;
import java.util.Random;

/**
 * @author CENTURY
 * @version 1.0
 * @date 2021/11/16 16:17
 * @description 随机算法
 **/
public class RandomBalance implements LoadBalance {

    private Random random = new Random();

    @Override
    public ServiceInfo chooseOne(List<ServiceInfo> serviceInfos) {
        return serviceInfos.get(random.nextInt(serviceInfos.size()));
    }
}
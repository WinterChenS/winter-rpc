package com.winterchen.core.balancer;

import com.winterchen.core.common.ServiceInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author winterchen
 * @version 1.0
 * @date 2021/11/16 16:15
 * @description 轮询算法
 **/
@Slf4j
public class FullRoundBalance implements LoadBalance {

    private int index;

    @Override
    public synchronized ServiceInfo chooseOne(List<ServiceInfo> serviceInfos) {
        if (index >= serviceInfos.size()) {
            index = 0;
        }
        return serviceInfos.get(index);
    }

}
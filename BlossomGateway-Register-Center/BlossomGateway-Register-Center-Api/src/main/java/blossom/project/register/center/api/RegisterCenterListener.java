package blossom.project.register.center.api;

import blossom.project.common.config.ServiceDefinition;
import blossom.project.common.config.ServiceInstance;

import java.util.Set;

/**
 * @author linqi
 * @version 1.0.0
 * @description 注册中心的监听器 用来监听注册中心的一些变化
 */
public interface RegisterCenterListener {

    void onChange(ServiceDefinition serviceDefinition,
                  Set<ServiceInstance> serviceInstanceSet);
}
package blossom.project.core.filter.loadbalance;

import blossom.project.common.config.ServiceInstance;
import blossom.project.core.context.GatewayContext;

/**
 * @author: linqi
 * LoadBalanceGatewayRule类
 * 负载均衡顶级接口
 */
public interface LoadBalanceGatewayRule {

    /**
     * 通过上下文参数获取服务实例
     * @param ctx
     * @param gray
     * @return
     */
    ServiceInstance choose(GatewayContext ctx,boolean gray);

    /**
     * 通过服务ID拿到对应的服务实例
     * @param serviceId
     * @param gray
     * @return
     */
    ServiceInstance choose(String serviceId,boolean gray);

}

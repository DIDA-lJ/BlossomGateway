package blossom.project.core.filter.flow;

import blossom.project.common.config.Rule;

/**
 * @author: linqi
 * GatewayFlowControlRule接口
 * 网关流控规则接口
 */
public interface GatewayFlowControlRule {

    /**
     * 执行流控规则过滤器
     * @param flowControlConfig
     * @param serviceId
     */
    void doFlowControlFilter(Rule.FlowControlConfig flowControlConfig, String serviceId);
}

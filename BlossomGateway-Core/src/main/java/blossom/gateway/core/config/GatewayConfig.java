package blossom.gateway.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linqi
 * @version 1.0.0
 * @description 网关配置 GatewayConfig类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GatewayConfig {
    /**
     * 项目端口
     */
    private int port = 8080;

    /**
     * 项目名称
     */
    private String applicationName = "blossom-gateway";

    /**
     * 注册中心地址
     */
    private String registryAddress = "localhost:8848";

    /**
     * 项目开发环境
     */
    private String env = "dev";


    //netty config

    /**
     * boss线程数量
     */
    private int eventLoopGroupBossNum = 1;

    /**
     * 工作线程数量
     */
    private int eventLoopGroupWorkerNum = Runtime.getRuntime().availableProcessors();

    /**
     * 报文最大长度
     */
    private int maxContentLength = 64 * 1024 * 1024;

    /**
     * 单双异步 默认单异步
     * false:单异步
     * true:双异步
     */
    private boolean oddEvenAsync = false;
}
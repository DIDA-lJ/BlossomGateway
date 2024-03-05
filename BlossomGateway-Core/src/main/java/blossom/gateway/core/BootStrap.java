package blossom.gateway.core;

import blossom.gateway.core.config.ConfigLoader;
import blossom.gateway.core.config.GatewayConfig;

/**
 * @author linqi
 * @version 1.0.0
 * @description 网关启动类
 */

public class BootStrap {
    public static void main(String[] args) {
        //加载网关核心静态配置
        GatewayConfig config = ConfigLoader.getInstance().load(args);
        System.out.println(config);
        //插件初始化
        //加载配置中心，监听配置中心的新增，修改，删除
        //启动容器
        //连接注册中心，将注册中心的实例加载到本地
        //服务优雅关机
    }
}
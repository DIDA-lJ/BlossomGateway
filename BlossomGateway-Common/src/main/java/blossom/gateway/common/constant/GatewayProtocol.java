package blossom.gateway.common.constant;

/**
 * @InterfaceName GatewayProtocol
 * @Description 网关协议类型
 * @Version 1.0.0
 * @Author LinQi
 * @Date 2024/03/11
 */
public interface GatewayProtocol {

    String HTTP = "http";

    String DUBBO = "dubbo";

    static boolean isHttp(String protocol) {
        return HTTP.equals(protocol);
    }

    static boolean isDubbo(String protocol) {
        return DUBBO.equals(protocol);
    }

}
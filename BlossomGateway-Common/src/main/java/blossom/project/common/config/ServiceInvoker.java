package blossom.project.common.config;

/**
 * @InterfaceName ServiceInvoker
 * @Description 服务调用的接口模型描述
 * @Version 1.0.0
 * @Author LinQi
 * @Date 2024/03/11
 */
public interface ServiceInvoker {

    /**
     * 获取真正的服务调用的全路径
     */
    String getInvokerPath();

    void setInvokerPath(String invokerPath);

    /**
     * 获取该服务调用(方法)的超时时间
     */
    int getTimeout();

    void setTimeout(int timeout);

}
package blossom.project.core;

/**
 * @InterfaceName LifeCycle
 * @Description LifeCycle接口
 * @Version 1.0.0
 * @Author LinQi
 * @Date 2024/03/05
 */
public interface LifeCycle {

    /**
     * 初始化
     */
    void init();

    /**
     * 启动
     */
    void start();


    /**
     * 关闭
     */
    void shutdown();


}
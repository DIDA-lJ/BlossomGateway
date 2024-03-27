package blossom.project.core.disruptor;

/**
 * @author: linqi
 *
 */
public interface ParallelQueue<E> {

    /**
     * 添加元素
     * @param event
     */
    void add(E event);
    void add(E... event);

    /**
     * 添加多个元素 返回是否添加成功的标志
     * @param event
     * @return
     */
    boolean tryAdd(E event);
    boolean tryAdd(E... event);

    /**
     * 启动
     */
    void start();

    /**
     * 销毁
     */
    void shutDown();
    /**
     * 判断是否已经销毁
     */
    boolean isShutDown();

}

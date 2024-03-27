package blossom.project.core.netty.processor;


import blossom.project.core.context.HttpRequestWrapper;
/**
 * @author: linqi
 */
public interface NettyProcessor {

    void process(HttpRequestWrapper wrapper);

    void  start();

    void shutDown();
}

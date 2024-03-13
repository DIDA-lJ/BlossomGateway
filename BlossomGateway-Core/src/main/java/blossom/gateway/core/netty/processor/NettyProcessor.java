package blossom.gateway.core.netty.processor;

import blossom.gateway.core.context.HttpRequestWrapper;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

public interface NettyProcessor {

    void process(HttpRequestWrapper wrapper);
}
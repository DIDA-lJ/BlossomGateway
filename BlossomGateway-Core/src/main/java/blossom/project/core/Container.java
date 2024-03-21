package blossom.project.core;

import blossom.project.core.netty.NettyHttpClient;
import blossom.project.core.netty.NettyHttpServer;
import blossom.project.core.netty.processor.NettyCoreProcessor;
import blossom.project.core.netty.processor.NettyProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linqi
 * @version 1.0.0
 * @description
 */

@Slf4j
public class Container implements LifeCycle {
    private final Config config;

    private NettyHttpServer nettyHttpServer;

    private NettyHttpClient nettyHttpClient;

    private NettyProcessor nettyProcessor;

    public Container(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        this.nettyProcessor = new NettyCoreProcessor();

        this.nettyHttpServer = new NettyHttpServer(config, nettyProcessor);

        this.nettyHttpClient = new NettyHttpClient(config,
                nettyHttpServer.getEventLoopGroupWoker());
    }

    @Override
    public void start() {
        nettyHttpServer.start();;
        nettyHttpClient.start();
        log.info("api gateway started!");
    }

    @Override
    public void shutdown() {
        nettyHttpServer.shutdown();
        nettyHttpClient.shutdown();
    }
}
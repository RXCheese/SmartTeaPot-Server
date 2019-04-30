package com.springboot.smartteapot.hardware.websocket;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.springboot.smartteapot.hardware.annotation.GizwitsWebsocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 若WebSocketListener存在并且 `gizwits.websocket.enabled` 为true则注入
 *
 */
@ConditionalOnProperty(value = "gizwits.websocket.enabled", havingValue = "true", matchIfMissing = true)
public class GizwitsWebsocketConfiguration {

    private Logger logger = LoggerFactory.getLogger(GizwitsWebsocketConfiguration.class);

    @Bean
    public ThreadPoolExecutor gizwitsExecutor() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNamePrefix("Gizwits-Pool-").build();
        return new ThreadPoolExecutor(10, 20, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);
    }

    @Bean
    @ConditionalOnMissingBean(annotation = GizwitsWebsocketListener.class)
    public DefaultGizwitsWebsocketListener defaultGizwitsWebsocketListener() {
        logger.info("没有注入标注【@GizwitsWebsocketListener】注解的【WebSocketListener】，使用默认的【DefaultGizwitsWebsocketListener】");
        return new DefaultGizwitsWebsocketListener();
    }

    @Bean
    public GizwitsWebsocket gizwitsWebsocket() {
        return new GizwitsWebsocket();
    }

    @Bean
    @ConditionalOnProperty(value = "gizwits.websocket.auto-config", havingValue = "true", matchIfMissing = true)
    public GizwitsWebsocketListenerAop gizwitsWebsocketListenerAop() {
        return new GizwitsWebsocketListenerAop();
    }

    @ConditionalOnProperty(value = "gizwits.websocket.auto-config", havingValue = "true", matchIfMissing = true)
    @Bean
    public GizwitsWebsocketAop gizwitsWebsocketAop() {
        return new GizwitsWebsocketAop();
    }

}

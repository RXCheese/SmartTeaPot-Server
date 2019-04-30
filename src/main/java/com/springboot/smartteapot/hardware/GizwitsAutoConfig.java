package com.springboot.smartteapot.hardware;

import com.springboot.smartteapot.hardware.openapi.GizwitsOpenApi;
import com.springboot.smartteapot.hardware.openapi.GizwitsOpenApiAop;
import com.springboot.smartteapot.hardware.properties.GizwitsOpenApiProperties;
import com.springboot.smartteapot.hardware.properties.GizwitsWebsocketProperties;
import com.springboot.smartteapot.hardware.utils.RequestUtil;
import com.springboot.smartteapot.hardware.websocket.GizwitsWebsocketConfiguration;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 机智云自动配置类
 */

@Configuration
@EnableScheduling
@EnableConfigurationProperties({
        GizwitsOpenApiProperties.class,
        GizwitsWebsocketProperties.class
})
@Import({
        GizwitsWebsocketConfiguration.class,
        GizwitsOpenApi.class,
        GizwitsOpenApiAop.class,
        RequestUtil.class
})
@AutoConfigureAfter
public class GizwitsAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}

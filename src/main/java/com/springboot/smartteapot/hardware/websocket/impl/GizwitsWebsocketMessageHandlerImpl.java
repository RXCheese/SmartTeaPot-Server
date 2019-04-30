package com.springboot.smartteapot.hardware.websocket.impl;

import com.springboot.smartteapot.hardware.entity.websocket.*;
import com.springboot.smartteapot.hardware.websocket.GizwitsWebsocket;
import com.springboot.smartteapot.hardware.websocket.GizwitsWebsocketMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 机智云websocket回调方法实现
 *
 * {@link GizwitsWebsocket#login()}对应回调{@link #onLogin(WsLoginRes)}
 * {@link GizwitsWebsocket#subscribe(List)}对应回调{@link #onSubscribe(WsSubscribeRes)}
 * {@link com.springboot.smartteapot.hardware.openapi.GizwitsOpenApi#bindDevice(String, String, String)}对应回调{@link #onBindingChanged(WsBindingChanged)}
 * {@link GizwitsWebsocket#read(String, List)}对应回调{@link #onNoti(WsNoti)}
 * {@link GizwitsWebsocket#ping()}对应回调{@link #onPong()}
 *
 */

@Component
public class GizwitsWebsocketMessageHandlerImpl implements GizwitsWebsocketMessageHandler {

    private Logger logger = LoggerFactory.getLogger(GizwitsWebsocketMessageHandlerImpl.class);

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    @Override
    public void onLogin(WsLoginRes wsLoginRes) {
        logger.info("onLogin");
    }

    @Override
    public void onSubscribe(WsSubscribeRes wsSubscribeRes) {
        logger.info("onSubscribe");
    }

    @Override
    public void onOnlineStatus(WsOnlineStatus wsOnlineStatus) {
        logger.info("onOnlineStatus");
    }

    @Override
    public void onBindingChanged(WsBindingChanged wsBindingChanged) {
        logger.info("onBindingChanged");
    }

    @Override
    public void onNoti(WsNoti wsNoti) {
        logger.info("onNoti");
    }

    @Override
    public void onPong() {
        logger.info("onPong");
    }

    @Override
    public void onInvalidMsg(WsInvalidMsg wsInvalidMsg) {
        logger.info("onInvalidMsg");
    }
}

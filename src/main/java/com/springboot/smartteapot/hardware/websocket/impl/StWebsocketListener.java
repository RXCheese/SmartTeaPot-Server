package com.springboot.smartteapot.hardware.websocket.impl;

import com.springboot.smartteapot.hardware.annotation.GizwitsWebsocketListener;
import com.springboot.smartteapot.hardware.websocket.GizwitsWebsocket;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GizwitsWebsocketListener
public class StWebsocketListener extends WebSocketListener {

    private Logger logger = LoggerFactory.getLogger(StWebsocketListener.class);

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    public StWebsocketListener() {
        logger.info("StWebsocketListener running");
//        super();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        logger.info("StWebsocketListener执行方法onOpen,发送登陆请求");
        gizwitsWebsocket.login();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        logger.info("onMessage running");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
    }

}

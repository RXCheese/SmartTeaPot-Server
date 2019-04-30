package com.springboot.smartteapot.hardware.websocket;

import okhttp3.WebSocketListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultGizwitsWebsocketListener extends WebSocketListener {
    private Logger logger = LoggerFactory.getLogger(DefaultGizwitsWebsocketListener.class);
}

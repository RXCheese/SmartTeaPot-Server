package com.springboot.smartteapot.hardware.websocket;

import com.google.gson.Gson;
import com.springboot.smartteapot.hardware.entity.websocket.*;
import com.springboot.smartteapot.hardware.enums.GizwitsWsEnum;
import okhttp3.Response;
import okhttp3.WebSocket;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 机智云WebsocketListener动态代理，监听{@link okhttp3.WebSocketListener}整个生命周期
 *
 */
@Aspect
public class GizwitsWebsocketListenerAop {

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    @Autowired
    private List<GizwitsWebsocketMessageHandler> messageHandlers;

    private Logger logger = LoggerFactory.getLogger(GizwitsWebsocketListenerAop.class);

    private Gson gson;

    {
        gson = new Gson();
    }

    @Pointcut(value = "execution(public * *.*WebSocketListener.*(..))")
    public void pointcut() {

    }

    @Before(value = "pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        Class<? extends GizwitsWebsocketListenerAop> aClass = getClass();
        try {
            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    method.invoke(this, args);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("exception");
            e.printStackTrace();
        }
    }

    public void onOpen(WebSocket webSocket, Response response) {
        logger.info("机智云websocket【onOpen】");
        gizwitsWebsocket.login();
    }

    public void onMessage(WebSocket webSocket, String text) {

        logger.info("机智云websocket【onMessage】，收到消息为【" + text + "】");
        WsEntity wsEntity = gson.fromJson(text, WsEntity.class);
        String cmd = wsEntity.getCmd();

        if (cmd.equals(GizwitsWsEnum.LOGIN_RES.getCmd())) {
            WsLoginRes wsLoginRes = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsLoginRes.class);
            handleLoginRes(wsLoginRes);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onLogin(wsLoginRes);
            }
        }

        if (cmd.equals(GizwitsWsEnum.SUBSCRIBE_RES.getCmd())) {
            WsSubscribeRes wsSubscribeRes = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsSubscribeRes.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onSubscribe(wsSubscribeRes);
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_ONLINE_STATUS.getCmd())) {
            WsOnlineStatus wsOnlineStatus = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsOnlineStatus.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onOnlineStatus(wsOnlineStatus);
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_BINDING_CHANGED.getCmd())) {
            WsBindingChanged wsBindingChanged = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsBindingChanged.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onBindingChanged(wsBindingChanged);
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_NOTI.getCmd())) {
            WsNoti wsNoti = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsNoti.class);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onNoti(wsNoti);
            }
        }

        if (cmd.equals(GizwitsWsEnum.PONG.getCmd())) {
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onPong();
            }
        }

        if (cmd.equals(GizwitsWsEnum.S2C_INVALID_MSG.getCmd())) {
            WsInvalidMsg wsInvalidMsg = gson.fromJson(JSONObject.valueToString(wsEntity.getData()), WsInvalidMsg.class);
            handleInvalidMsg(wsInvalidMsg);
            for (GizwitsWebsocketMessageHandler messageHandler : messageHandlers) {
                messageHandler.onInvalidMsg(wsInvalidMsg);
            }
        }
    }

    public void onClosing(WebSocket webSocket, int code, String reason) {
        logger.info("机智云websocket【onClosing】，code【" + code + "】，reason【" + reason + "】");
        gizwitsWebsocket.init();
    }

    public void onClosed(WebSocket webSocket, int code, String reason) {
        logger.info("机智云websocket【onClosed】，code【" + code + "】，reason【" + reason + "】");
        gizwitsWebsocket.init();
    }

    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        logger.info("机智云websocket【onFailure】，Throwable【" + t + "】");
        gizwitsWebsocket.init();
    }

    /**
     * 处理登录响应
     *
     * @param wsLoginRes
     */
    private void handleLoginRes(WsLoginRes wsLoginRes) {
        if (wsLoginRes.getSuccess()) {
            logger.info("机智云websocket登录成功");
        } else {
            logger.info("机智云websocket登录失败，2秒后重新登录");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gizwitsWebsocket.login();
        }
    }

    /**
     * 处理非法信息
     *
     * @param wsInvalidMsg
     */
    private void handleInvalidMsg(WsInvalidMsg wsInvalidMsg) {
        if (wsInvalidMsg.getError_code() == 1009) {
            logger.error("M2M socket has closed, please login again!");
            logger.error("机智云websocket登录超时，重新进行websocket登录");
            gizwitsWebsocket.login();
        }
    }

    /**
     * 心跳
     */
    @Scheduled(cron = "${gizwits.websocket.heartbeatInterval}")
    private void handleHeartbeat() {
        gizwitsWebsocket.ping();
    }
}

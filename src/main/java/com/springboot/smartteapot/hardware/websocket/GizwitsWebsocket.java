package com.springboot.smartteapot.hardware.websocket;


import com.springboot.smartteapot.hardware.annotation.GizwitsWebsocketListener;
import com.springboot.smartteapot.hardware.entity.websocket.*;
import com.springboot.smartteapot.hardware.enums.GizwitsWsEnum;
import com.springboot.smartteapot.hardware.openapi.GizwitsOpenApi;
import com.springboot.smartteapot.hardware.properties.GizwitsOpenApiProperties;
import com.springboot.smartteapot.hardware.properties.GizwitsWebsocketProperties;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GizwitsWebsocket {

    @Autowired
    private GizwitsWebsocketProperties gizwitsWebsocketProperties;

    @Autowired
    private GizwitsOpenApiProperties gizwitsOpenApiProperties;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private List<WebSocketListener> webSocketListeners;

    private WebSocket webSocket;

    @Autowired
    private GizwitsOpenApi gizwitsOpenApi;

    Logger logger = LoggerFactory.getLogger(GizwitsWebsocket.class);

    @PostConstruct
    public void init() {
        logger.info("初始化websocket");
        Request request = new Request.Builder().url(gizwitsWebsocketProperties.getUrl()).build();
        WebSocketListener webSocketListener = null;
        for (WebSocketListener listener : webSocketListeners) {
            Class<? extends WebSocketListener> aClass = listener.getClass();
            String name = aClass.getCanonicalName();
            // 容器存在多个WebsocketListener，若有默认的DefaultGizwitsWebsocketListener，则使用该Listener
            if (name.contains("com.springboot.smartteapot.hardware.websocket.DefaultGizwitsWebsocketListener")) {
                webSocketListener = listener;
                break;
            }
            GizwitsWebsocketListener annotation = aClass.getAnnotation(GizwitsWebsocketListener.class);
            // 容器存在多个标注 @GizwitsWebsocketListener 注解的 WebsocketListener，默认使用第一个Listener
            if (annotation != null) {
                if (webSocketListeners.size() > 1) {
                    logger.warn("存在多个标注【@GizwitsWebsocketListener】注解的【WebsocketListener】，使用了【" + name + "】");
                } else {
                    logger.info("存在【@GizwitsWebsocketListener】注解的【WebsocketListener】，使用了【" + name + "】");
                }
                webSocketListener = listener;
                break;
            }
        }
        webSocket = okHttpClient.newWebSocket(request, webSocketListener);
    }

    /**
     * 机智云websocket登录，若登录失败2秒后会重新登录
     *
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean login() {
        WsEntity<WsLoginReq> loginWsEntity = new WsEntity<>();
        loginWsEntity.setCmd(GizwitsWsEnum.LOGIN_REQ.getCmd());
        WsLoginReq wsLogin = new WsLoginReq();
        wsLogin.setAppid(gizwitsOpenApiProperties.getAppId());
        wsLogin.setUid(gizwitsOpenApi.login().getUid());
        wsLogin.setToken(gizwitsOpenApi.login().getToken());
        wsLogin.setP0_type(gizwitsWebsocketProperties.getP0Type().getType());
        wsLogin.setHeartbeat_interval(gizwitsWebsocketProperties.getHeartbeatTimeout());
        wsLogin.setAuto_subscribe(gizwitsWebsocketProperties.getAutoSubscribe());
        loginWsEntity.setData(wsLogin);
        return webSocket.send(new JSONObject(loginWsEntity).toString());
    }

    /**
     * 机智云websocket订阅已绑定的dids
     *
     * @param dids did 列表
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean subscribe(List<String> dids) {
        WsEntity<List<WsSubscribeReq>> listWsEntity = new WsEntity<>();
        listWsEntity.setCmd(GizwitsWsEnum.SUBSCRIBE_REQ.getCmd());
        List<WsSubscribeReq> wsSubscribeReqs = new ArrayList<>();
        for (String did : dids) {
            WsSubscribeReq wsSubscribeReq = new WsSubscribeReq();
            wsSubscribeReq.setDid(did);
            wsSubscribeReqs.add(wsSubscribeReq);
        }
        listWsEntity.setData(wsSubscribeReqs);
        return webSocket.send(new JSONObject(listWsEntity).toString());
    }

    /**
     * 读取数据点
     *
     * @param did 设备did
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean read(String did) {
        return read(did, null);
    }

    /**
     * 读取数据点
     *
     * @param did   设备did
     * @param names （变长数据点可选参数：传入需要读取的数据点名称，参数省略表示读取全部数据点；定长数据点读操作忽略该参数）
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean read(String did, List<String> names) {
        WsEntity<WsRead> readWsEntity = new WsEntity<>();
        readWsEntity.setCmd(GizwitsWsEnum.C2S_READ.getCmd());
        WsRead wsRead = new WsRead();
        wsRead.setDid(did);
        wsRead.setNames(names);
        readWsEntity.setData(wsRead);
        return webSocket.send(new JSONObject(readWsEntity).toString());
    }

    /**
     * 写入数据点
     *
     * @param did   设备did
     * @param attrs 数据点
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public boolean write(String did, Map<String, Object> attrs) {
        WsEntity<WsWrite> writeWsEntity = new WsEntity<>();
        writeWsEntity.setCmd(GizwitsWsEnum.C2S_WRITE.getCmd());
        WsWrite wsWrite = new WsWrite();
        wsWrite.setDid(did);
        wsWrite.setAttrs(attrs);
        writeWsEntity.setData(wsWrite);
        return webSocket.send(new JSONObject(writeWsEntity).toString());
    }

    /**
     * 发送心跳
     *
     * @return 若启动了自动配置则永远返回true，若websocket断开连接，会自动重新发送
     * @see GizwitsWebsocketProperties#getAutoConfig()
     */
    public void ping() {
        WsEntity wsEntity = new WsEntity();
        wsEntity.setCmd(GizwitsWsEnum.PING.getCmd());
        webSocket.send(new JSONObject(wsEntity).toString());
    }



    public void cancel() {
        webSocket.cancel();
    }

    public GizwitsWebsocketProperties getGizwitsWebsocketProperties() {
        return gizwitsWebsocketProperties;
    }

    public void setGizwitsWebsocketProperties(GizwitsWebsocketProperties gizwitsWebsocketProperties) {
        this.gizwitsWebsocketProperties = gizwitsWebsocketProperties;
    }

    public GizwitsOpenApiProperties getGizwitsOpenApiProperties() {
        return gizwitsOpenApiProperties;
    }

    public void setGizwitsOpenApiProperties(GizwitsOpenApiProperties gizwitsOpenApiProperties) {
        this.gizwitsOpenApiProperties = gizwitsOpenApiProperties;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public List<WebSocketListener> getWebSocketListeners() {
        return webSocketListeners;
    }

    public void setWebSocketListeners(List<WebSocketListener> webSocketListeners) {
        this.webSocketListeners = webSocketListeners;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public GizwitsOpenApi getGizwitsOpenApi() {
        return gizwitsOpenApi;
    }

    public void setGizwitsOpenApi(GizwitsOpenApi gizwitsOpenApi) {
        this.gizwitsOpenApi = gizwitsOpenApi;
    }
}

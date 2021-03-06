package com.springboot.smartteapot.hardware.websocket;

import com.springboot.smartteapot.hardware.entity.websocket.WsLoginRes;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Aspect
public class GizwitsWebsocketAop implements GizwitsWebsocketMessageHandler {

    private static final String LOGIN_METHOD_NAME = "login";

    private Logger logger = LoggerFactory.getLogger(GizwitsWebsocketAop.class);

    /**
     * 存放发送失败消息队列
     */
    private List<Map<String, Object[]>> unsentMessageQueue = new ArrayList<>();

    private Lock lock = new ReentrantLock(false);

    @Autowired
    private ThreadPoolExecutor gizwitsExecutor;

    @Autowired
    private GizwitsWebsocket gizwitsWebsocket;

    @Pointcut("execution(public * com.springboot.smartteapot.hardware.websocket.GizwitsWebsocket.*(..))")
    private void pointcut() {

    }

    @Around(value = "pointcut()")
    public Object onAround(final ProceedingJoinPoint proceedingJoinPoint) {

        Runnable runnable = () -> {
            lock.lock();
            logger.trace("获得锁！" + Thread.currentThread().getId() + " nano：" + LocalTime.now().getNano());
            try {
                Object result = proceedingJoinPoint.proceed();
                String name = proceedingJoinPoint.getSignature().getName();
                Object[] args = proceedingJoinPoint.getArgs();

                logger.info("机智云websocket执行了【" + name + "】方法，参数为【" + Arrays.toString(args) + "】");

                if (result instanceof Boolean && result.equals(Boolean.FALSE)) {
                    // 机智云websocket发送失败，把发送失败的消息放入队列中
                    if (!LOGIN_METHOD_NAME.equals(name)) {
                        HashMap<String, Object[]> unsentMessage = new HashMap<>(4);
                        unsentMessage.put(name, args);
                        unsentMessageQueue.add(unsentMessage);
                        logger.error("机智云websocket ---->>>> 方法：" + name + " 执行失败，参数：" + Arrays.toString(args));
                    }
                    gizwitsWebsocket.cancel();
                    // 重新连接websocket
//                    gizwitsWebsocket.init();
                }

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            } finally {
                logger.trace("失去锁！" + Thread.currentThread().getId() + " nano：" + LocalTime.now().getNano());
                lock.unlock();
            }
        };
        Thread thread = gizwitsExecutor.getThreadFactory().newThread(runnable);
        thread.start();

        return true;
    }

    @Override
    public void onLogin(WsLoginRes wsLoginRes) {

        if (!wsLoginRes.getSuccess() || unsentMessageQueue.size() == 0) {
            return;
        }
        handleUnsendMessageQueue();
    }

    /**
     * 处理未成功发送的消息队列
     */
    private void handleUnsendMessageQueue() {
        logger.info("登录成功，处理未发送的消息队列");
        try {
            lock.lock();
            logger.trace("获得锁！！" + Thread.currentThread().getId() + " nano：" + LocalTime.now().getNano());
            Iterator<Map<String, Object[]>> iterator = unsentMessageQueue.iterator();
            logger.info("Before 未处理消息：" + unsentMessageQueue.size());
            while (iterator.hasNext()) {
                Map<String, Object[]> next = iterator.next();
                iterator.remove();
                next.forEach((methodName, args) -> {
                    Class<? extends GizwitsWebsocket> aClass = gizwitsWebsocket.getClass();
                    try {
                        Method[] methods = aClass.getMethods();
                        for (Method method : methods) {
                            if (method.getName().equals(methodName)) {
                                method.invoke(gizwitsWebsocket, args);
                                logger.info("重新执行 " + methodName + " 方法");
                                break;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("exception");
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            logger.info("After 未处理消息：" + unsentMessageQueue.size());
            logger.trace("释放锁！！" + Thread.currentThread().getId() + " nano：" + LocalTime.now().getNano());
            lock.unlock();
        }
    }
}

package com.example.sqlserver_websocket.socket;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/websocket")  // 接收websocket 请求路径
@Component
public class WebSocketServer {

    // 静态变量， 用来记录当前在线连接数，
    private static int onlineCount = 0;

    //concurrent 包的线程安全set,用来村吃每个客户端对应的WebSocket 对象
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    // 与某个客户端的连接会话，需要通过它来实现给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */

    @OnOpen
    public void onOpen(Session session){

        this.session = session;
        // 加入set
        webSocketSet.add(this);
        // 在线数加1
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为：" + getOnlineCount());
        try {
//            sendMessage("连接成功"+InitDataSend.bufferData);
            // Json 序列化
            String Initdata = JSON.toJSONString(InitDataSend.bufferData);
            //sendMessage("连接成功"+Initdata);
            sendMessage("连接成功");
            //
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        // 从set 中删除
        webSocketSet.remove(this);
        // 在线人数减1
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的信息
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("来自客户端的消息：" + message);

        //群发消息
        for (WebSocketServer item : webSocketSet){
            try{
                item.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    /**
     * 发生错误信息时，调用
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("错误信息");
        error.printStackTrace();
    }

    /**
     * @Descripation: 实现服务器主动发送信息
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * @Description: 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException{
        for(WebSocketServer item: webSocketSet){
            try{
                item.sendMessage(message);
            }catch(IOException e){
                continue;
            }
        }
    }


    /**
     * @Description 获取当前在线人数
     */
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    /**
     * @Description: 在线人数+1
     */
    public static synchronized void addOnlineCount(){
        WebSocketServer.onlineCount++;
    }

    /**
     * @Description: 在线人数-1
     */
    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount--;
    }
}

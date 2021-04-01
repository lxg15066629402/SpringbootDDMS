package com.example.sqlserver_websocket.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Jone
 * @describe 开启WebSocket 支持
 */
@Configuration
public class WebSocketConfig {
    //
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}


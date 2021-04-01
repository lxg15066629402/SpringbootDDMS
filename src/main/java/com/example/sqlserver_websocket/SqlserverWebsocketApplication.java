package com.example.sqlserver_websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SqlserverWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlserverWebsocketApplication.class, args);
/*        QuartData quartData = new QuartData();
        quartData.run();*/

    }

}

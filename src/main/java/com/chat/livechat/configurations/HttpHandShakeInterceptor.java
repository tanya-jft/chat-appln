package com.chat.livechat.configurations;


import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@Slf4j
public class HttpHandShakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
       log.info("Hand shake interceptors");
       if(request instanceof ServletServerHttpRequest ){
           ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
           HttpSession session = serverHttpRequest.getServletRequest().getSession();
           if(session != null){
               attributes.put("sessionId", session.getId());
               log.info("Session id value {}" ,session.getAttribute("101"));
           }
       }
       return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

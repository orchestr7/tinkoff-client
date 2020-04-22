package ru.tinkoff.client.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppContext {
    private final static String SOCKET_PREFIX = "wss://";

    @Value("${host}")
    private String host;

    @Value("${socket.endpoint}")
    private String enpoint;

    public String getTargetUrlForSocket() {
        return SOCKET_PREFIX + host + enpoint;
    }
}

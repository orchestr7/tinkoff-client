package ru.tinkoff.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.client.socket.SocketClient;

@RestController
@RequestMapping("/test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private SocketClient socketClient;

    @Autowired
    private TokenUtils token;

    @GetMapping(value = "/1")
    public void control() {
        try {
            socketClient.doRequest();
        } catch (Exception e) {
            log.error("exception", e);
        }
    }
}

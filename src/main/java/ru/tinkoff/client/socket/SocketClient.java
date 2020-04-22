package ru.tinkoff.client.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.client.TokenUtils;
import ru.tinkoff.client.common.AppContext;

import java.net.URI;
import java.time.Duration;

@Service
public class SocketClient {
    private Logger log = LoggerFactory.getLogger(SocketClient.class);

    @Autowired
    private AppContext context;

    @Autowired
    private TokenUtils tokenUtils;

    public void doRequest() throws InterruptedException {
        WebSocketClient client = new ReactorNettyWebSocketClient();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, tokenUtils.getToken());


        client.execute(
                URI.create(context.getTargetUrlForSocket()),
                headers,
                session -> session.send(
                        Mono.just(session.textMessage("{\"event\": \"orderbook:subscribe\", \"figi\": \"BBG0013HGFT4\", \"depth\": 3, \"request_id\": \"123ASD1123\"}")))
                        .thenMany(session.receive()
                                .map(WebSocketMessage::getPayloadAsText)
                                .doOnNext(x -> System.out.println("LOGGING" + x))
                                .log())
                        .then())
                .block(Duration.ofSeconds(100L));
    }
}

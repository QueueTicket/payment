package com.qticket.payment.adapter.out.web.config.webclient.base;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

public class HttpClientConfig {

    private static HttpClient baseHttpClient(HttpClient httpClient) {
        return httpClient
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3_000)
            .responseTimeout(Duration.ofSeconds(10)) // 응답 타임아웃 10초
            .doOnConnected(connection ->
                connection.addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS)) // 읽기 타임아웃
                    .addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS))
            );
    }

    public static HttpClient defaultHttpClient() {
        return HttpClient.create();
    }

    public static HttpClient paymentHttpClient(ConnectionProvider connectionProvider) {
        return HttpClient.create(connectionProvider);
    }

}

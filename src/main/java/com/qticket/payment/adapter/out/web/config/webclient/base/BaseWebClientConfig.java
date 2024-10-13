package com.qticket.payment.adapter.out.web.config.webclient.base;

import static com.qticket.payment.adapter.out.web.config.webclient.base.HttpClientConfig.defaultHttpClient;
import static com.qticket.payment.adapter.out.web.config.webclient.base.RequestLogFilter.addCustomHeaderFilter;
import static com.qticket.payment.adapter.out.web.config.webclient.base.RequestLogFilter.logRequestFilter;
import static com.qticket.payment.adapter.out.web.config.webclient.base.ResponseLogFilter.logResponseFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BaseWebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .clientConnector(reactorClientHttpConnector())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) // 메모리 제한 2MB
            .filter(addCustomHeaderFilter())
            .filter(logRequestFilter())
            .filter(logResponseFilter())
            .build();
    }

    private ReactorClientHttpConnector reactorClientHttpConnector() {
        return new ReactorClientHttpConnector(defaultHttpClient());
    }

}

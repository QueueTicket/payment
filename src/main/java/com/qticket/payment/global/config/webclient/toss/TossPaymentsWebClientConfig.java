package com.qticket.payment.global.config.webclient.toss;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.qticket.payment.global.config.webclient.base.BaseWebClientConfig;
import com.qticket.payment.global.config.webclient.base.HttpClientConfig;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@RequiredArgsConstructor
public class TossPaymentsWebClientConfig {

    private static final String BASIC_PREFIX = "Basic ";

    private final BaseWebClientConfig baseWebClientConfig;
    private final ConnectionProvider connectionProvider;
    private final TossPaymentsProperties tossPaymentsProperties;

    @Bean
    public WebClient tossPaymentsWebClient() {
        String basicHeader = toBasicHeaderWithoutPasswordFromSecretKey(tossPaymentsProperties.secretKey());
        return baseWebClientConfig.webClient().mutate()
            .baseUrl(tossPaymentsProperties.baseUrl())
            .defaultHeader(HttpHeaders.AUTHORIZATION, basicHeader)
            .clientConnector(reactorClientHttpConnector())
            .build();
    }

    private String toBasicHeaderWithoutPasswordFromSecretKey(String secretKey) {
        String encodedSecretKey = encodeToBase64(secretKey);
        return BASIC_PREFIX + encodedSecretKey;
    }

    private String encodeToBase64(String plainSecretKey) {
        String withoutPasswordSecretKey = addWithoutPasswordSecretKey(plainSecretKey);
        return Base64.getEncoder()
            .encodeToString(withoutPasswordSecretKey.getBytes(UTF_8));
    }

    private String addWithoutPasswordSecretKey(String plainSecretKey) {
        return plainSecretKey + ":";
    }

    private ReactorClientHttpConnector reactorClientHttpConnector() {
        return new ReactorClientHttpConnector(HttpClientConfig.paymentHttpClient(connectionProvider));
    }

}

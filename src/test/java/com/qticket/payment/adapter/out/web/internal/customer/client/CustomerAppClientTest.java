package com.qticket.payment.adapter.out.web.internal.customer.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.qticket.payment.adapter.out.web.internal.customer.client.response.CustomerResponse;
import com.qticket.payment.base.wiremock.InternalClientTestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Adapter:Out:Web:Internal:Customer")
class CustomerAppClientTest extends InternalClientTestBase {

    private final CustomerAppClient customerAppClient;

    public CustomerAppClientTest(CustomerAppClient customerAppClient) {
        this.customerAppClient = customerAppClient;
    }

    @Test
    @DisplayName("회원 조회 API")
    void getCustomer() {
        // Given
        final Long userId = 1L;

        // When
        CustomerResponse response = customerAppClient.getCustomer(userId);

        // Then
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("홍길동");
        assertThat(response.email()).isEqualTo("test@gmail.com");
    }

}

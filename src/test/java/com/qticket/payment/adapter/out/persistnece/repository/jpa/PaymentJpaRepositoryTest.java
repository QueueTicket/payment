package com.qticket.payment.adapter.out.persistnece.repository.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.qticket.payment.adapter.out.persistnece.repository.jpa.entity.PaymentJpaEntity;
import com.qticket.payment.config.base.TestBase;
import com.qticket.payment.config.p6spy.P6spySqlFormatConfig;
import com.qticket.payment.global.jpa.JpaConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(showSql = false)
@Import({JpaConfig.class, P6spySqlFormatConfig.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
@DisplayName("Repo:Payment")
class PaymentJpaRepositoryTest extends TestBase {

    private final PaymentJpaRepository paymentJpaRepository;

    public PaymentJpaRepositoryTest(PaymentJpaRepository paymentJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
    }

    @Sql("classpath:data/payment.sql")
    @Test
    @DisplayName("결제 정보 조회")
    void findPaymentWithItemsAndBenefitByOrderId() {
        // Given
        final String given = "9c7bf46c-3b77-4a56-9c17-e944d0b8de41";

        // When
        PaymentJpaEntity actual = paymentJpaRepository.findPaymentWithItemsAndBenefitByOrderId(given).orElseThrow();

        // Then
        assertThat(actual.getPaymentItemElements()).hasSize(2);
    }

}

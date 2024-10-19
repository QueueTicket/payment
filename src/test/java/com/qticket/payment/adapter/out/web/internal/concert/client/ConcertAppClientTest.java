package com.qticket.payment.adapter.out.web.internal.concert.client;

import static org.assertj.core.api.Assertions.assertThat;

import com.qticket.payment.adapter.out.web.internal.concert.client.response.ConcertSeatResponse;
import com.qticket.payment.adapter.out.web.internal.concert.client.response.PriceResponse;
import com.qticket.payment.config.wiremock.InternalClientTestBase;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Adapter:Out:Web:Internal:Concert")
class ConcertAppClientTest extends InternalClientTestBase {

    private final ConcertAppClient concertAppClient;

    public ConcertAppClientTest(ConcertAppClient concertAppClient) {
        this.concertAppClient = concertAppClient;
    }

    @Test
    @DisplayName("예약 공연 좌석 조회 API")
    void getReservedConcertSeats() {
        // Given
        final String concertId = "concert-id";

        // When
        List<ConcertSeatResponse> actual = concertAppClient.getReservedConcertSeats(concertId);

        // Then
        assertThat(actual).hasSize(3);
    }

    @Test
    @DisplayName("좌석 가격 조회 API")
    void getTicketPrices() {
        // Given
        final List<String> given = List.of("seat-A", "seat-B");

        // When
        List<PriceResponse> actual = concertAppClient.getTicketPrices(given);

        // Then
        assertThat(actual).hasSize(3);
    }

}

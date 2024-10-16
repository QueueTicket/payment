package com.qticket.payment.base.wiremock;

import com.qticket.payment.config.feign.InternalFeignMockUrlConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import(InternalFeignMockUrlConfig.class)
@TestPropertySource(properties = "wiremock.base.url=http://localhost:${wiremock.server.port}")
public abstract class InternalClientTestBase extends WiremockTestBase {

}

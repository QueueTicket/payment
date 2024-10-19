package com.qticket.payment.config.wiremock;

import com.qticket.payment.config.base.SpringBootTestBase;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@AutoConfigureWireMock(port = 0, stubs = "classpath:mappings")
public abstract class WiremockTestBase extends SpringBootTestBase {

}

package com.qticket.payment.base.wiremock;

import com.qticket.payment.base.SpringBootTestBase;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@AutoConfigureWireMock(port = 0, stubs = "classpath:mappings")
public abstract class WiremockTestBase extends SpringBootTestBase {

}

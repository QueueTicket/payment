package com.qticket.payment.base;

import static com.qticket.payment.base.TestBase.TEST;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@ActiveProfiles(TEST)
@TestConstructor(autowireMode = AutowireMode.ALL)
public abstract class TestBase {

    static final String TEST = "test";

}

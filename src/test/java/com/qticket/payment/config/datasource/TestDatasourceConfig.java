package com.qticket.payment.config.datasource;

import com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration;
import com.qticket.payment.config.p6spy.P6spySqlFormatConfig;
import com.qticket.payment.global.jpa.JpaConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, P6spySqlFormatConfig.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ImportAutoConfiguration(DataSourceDecoratorAutoConfiguration.class)
public abstract class TestDatasourceConfig {

}

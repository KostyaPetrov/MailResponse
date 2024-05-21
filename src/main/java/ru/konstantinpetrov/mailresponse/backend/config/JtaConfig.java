package ru.konstantinpetrov.mailresponse.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import bitronix.tm.TransactionManagerServices;
import bitronix.tm.BitronixTransactionManager;

@Configuration
@EnableTransactionManagement
public class JtaConfig {

    @Bean
    public BitronixTransactionManager bitronixTransactionManager() {
        return TransactionManagerServices.getTransactionManager();
    }
}

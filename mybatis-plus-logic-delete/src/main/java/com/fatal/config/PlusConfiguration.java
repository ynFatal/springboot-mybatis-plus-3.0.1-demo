package com.fatal.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;

@Configuration
public class PlusConfiguration {

    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }
}

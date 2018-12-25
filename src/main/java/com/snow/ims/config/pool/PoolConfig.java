package com.snow.ims.config.pool;

import com.snow.ims.common.pool.StandardThreadExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class PoolConfig {



    @Bean
    public StandardThreadExecutor standardThreadExecutor(){
        return new StandardThreadExecutor();
    }




}

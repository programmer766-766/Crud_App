package com.crud.application.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimitingConfig{

    @Bean
   public Bucket bucket(){

        return Bucket
                .builder()
                .addLimit(limit->limit.capacity(2)
                        .refillGreedy(2,Duration.ofMinutes(3))).build();
    }
}

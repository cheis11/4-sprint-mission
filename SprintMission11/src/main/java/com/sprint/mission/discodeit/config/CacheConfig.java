package com.sprint.mission.discodeit.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    CaffeineCacheManager cacheManager = new CaffeineCacheManager("users", "channels", "notifications");
    cacheManager.setCaffeine(Caffeine.newBuilder()
        .maximumSize(100)           // 최대 100개 캐시
        .expireAfterAccess(600, TimeUnit.SECONDS) // 마지막 접근 기준 10분 후 만료
        .recordStats()              // 통계 기록
    );
    return cacheManager;
  }
}

package com.sprint.mission.discodeit.config;

import com.sprint.mission.discodeit.async.AsyncProperties;
import com.sprint.mission.discodeit.async.MdcTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

  private final AsyncProperties asyncProperties;

  public AsyncConfig(AsyncProperties asyncProperties) {
    this.asyncProperties = asyncProperties;
  }

  @Bean(name = "binaryContentTaskExecutor")
  public TaskExecutor binaryContentTaskExecutor() {
    return buildExecutor(asyncProperties.getBinaryContent(), "binaryContent-");
  }

  @Bean(name = "notificationTaskExecutor")
  public TaskExecutor notificationTaskExecutor() {
    return buildExecutor(asyncProperties.getNotification(), "notification-");
  }

  private ThreadPoolTaskExecutor buildExecutor(AsyncProperties.Pool pool, String threadNamePrefix) {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(pool.getCorePoolSize());
    executor.setMaxPoolSize(pool.getMaxPoolSize());
    executor.setQueueCapacity(pool.getQueueCapacity());
    executor.setThreadNamePrefix(threadNamePrefix);
    executor.setTaskDecorator(new MdcTaskDecorator());
    executor.initialize();
    return executor;
  }
}

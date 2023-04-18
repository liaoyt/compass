package com.oppo.cloud.diagnosis.config;

import lombok.Data;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * kafka配置
 */
@Configuration
@EnableKafka
@Data
public class KafkaConfig {
    /**
     * 消费主题
     */
    @Value("${spring.kafka.realtimeTaskTopic}")
    private String realtimeTaskTopic;
    /**
     * 消费组
     */
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    /**
     * 消费模式: lastest, earliest
     */
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    /**
     * kafka broker集群地址
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    /**
     * 两次消费最大间隔时间
     */
    @Value("${spring.kafka.consumer.max-poll-interval-ms}")
    private String maxPollIntervalMs;
    /**
     * 创建消费者
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(), new StringDeserializer(), new StringDeserializer());
    }
    /**
     * 消费者配置
     */
    public Map<String, Object> consumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerClientId());
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollIntervalMs);
        config.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());
        return config;
    }

    /**
     * 配置listener
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String>
    kafkaListenerContainerFactory(ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    /**
     * 获取clientId: 表现为kafka memberId前缀
     */
    @Bean(name = "consumerId")
    public String consumerClientId() {
        return String.format("client-%d-%d", Thread.currentThread().getId(), new Random().nextInt());
    }
    /**
     * 获取主题
     */
    @Bean(name = "topics")
    public String getTopics() {
        return this.realtimeTaskTopic;
    }
    /**
     * 获取brokers
     */
    @Bean(name = "bootstrapServers")
    public String getBootstrapServers() {
        return this.bootstrapServers;
    }
    /**
     * 获取groupId
     */
    @Bean(name = "groupId")
    public String getGroupId() {
        return this.groupId;
    }
    /**
     * 使用Kafka Admin Client API操作
     */
    @Bean(name = "kafkaAdminClient")
    public AdminClient kafkaAdminClient() {
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return AdminClient.create(properties);
    }
}

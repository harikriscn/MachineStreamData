package com.zeiss;

import com.zeiss.service.MachineStreamService;
import com.zeiss.service.MachineStreamServiceImpl;
import com.zeiss.service.WebSocketClientService;
import com.zeiss.service.WebSocketClientServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringConfigured
@ComponentScans(value={
        @ComponentScan(basePackages = "com.zeiss.service"),
        @ComponentScan(basePackages = "com.zeiss.repository"),
        @ComponentScan(basePackages = "com.zeiss.websocket")
})
@PropertySource("classpath:properties/application.properties")
@EnableJms
public class MachineStreamApp {
    public static void main(String[] args) {
        SpringApplication.run(MachineStreamApp.class, args);
    }

    @Bean
    MachineStreamService getmachineStreamService() {
        return new MachineStreamServiceImpl();
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {

        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    JmsTemplate getJmsTemplate(ConnectionFactory connectionFactory) {
        return new JmsTemplate(connectionFactory);
    }

    @Bean
    WebSocketClientService getClientServiceImpl() {
        return new WebSocketClientServiceImpl();
    }
    /*@Bean
    MachineStreamRepository getmachineStreamRepository() {
        return new MachineStreamRepositoryImpl();
    }*/
    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}

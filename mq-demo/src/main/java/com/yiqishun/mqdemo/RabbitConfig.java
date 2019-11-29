package com.yiqishun.mqdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//错误的 import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

/**
 *
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。 Queue:消息的载体,每个消息都会被投到一个或多个队列。
 *
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 *
 * Routing Key:路由关键字,
 *
 * exchange根据这个关键字进行消息投递。 vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 *
 * Producer:消息生产者,就是投递消息的程序. Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 *
 *
 */

@Configuration
public class RabbitConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE_YQS = "YQS_TOPIC";

    public static final String QUEUE_SMALL_PROGRAM = "";//填写在易企顺的对列名，具体联系易企顺获取

    public static final String ROUTINGKEY_SMALL_PROGRAM = "";


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);


        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    // 必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /**
     * * 针对消费者配置 *
     * 1. 设置交换机类型 *
     * 2. 将队列绑定到交换机
     *
     * FanoutExchange:
     * 将消息分发到所有的绑定队列，无routingkey的概念
     *
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     *
     * 如果需要使用的其他的交换器类型，spring中都已提供实现，所有的交换器均实现org.springframework.amqp.core.AbstractExchange接口。
     常用交换器类型如下：

     Direct(DirectExchange)：direct 类型的行为是"先匹配, 再投送".
     即在绑定时设定一个 routing_key, 消息的routing_key完全匹配时, 才会被交换器投送到绑定的队列中去。

     Topic(TopicExchange)：按规则转发消息（最灵活）。

     Headers(HeadersExchange)：设置header attribute参数类型的交换机。

     Fanout(FanoutExchange)：转发消息到所有绑定队列。

     *
     */








//    @Bean
//    public TopicExchange defaultExchange() {
//        return new TopicExchange(EXCHANGE_YQS);
//    }
//
//    /**
//     * 获取队列A
//     *
//     * @return
//     */
//    @Bean
//    public Queue queueA() {
//        Queue queue=new Queue(QUEUE_SMALL_PROGRAM, true);// 队列持久
//        return  queue;
//    }
//
//
//
//    /**
//     * 一个交换机可以绑定多个消息队列，也就是消息通过一个交换机，可以分发到不同的队列当中去。
//     *
//     * @return
//     */
//    @Bean
//    public Binding binding() {
//
//        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_SMALL_PROGRAM);
//    }







}


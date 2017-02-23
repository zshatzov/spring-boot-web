package com.apress.spring.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPMessagingConfiguration {
	
	private static final Logger LOG = 
				LoggerFactory.getLogger(AMQPMessagingConfiguration.class);
	
	final static String QUEUE_NAME = "spring-boot";
	
	@Autowired
	private MessageConverter converter;

	public AMQPMessagingConfiguration() {
		// TODO Auto-generated constructor stub
	}
	
	@Bean
	public Queue queue(){
		return new Queue(QUEUE_NAME, false);
	}
	
	@Bean
	public TopicExchange exchange(){
		return new TopicExchange("spring-boot-exchange", false, false);
	}
	
	@Bean
	public Binding binding(Queue queue, TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}
	
	@Bean
	public MessageListenerAdapter listenerAdapter(Receiver receiver){
		MessageListenerAdapter adapter =  new MessageListenerAdapter(receiver); 
		adapter.setDefaultListenerMethod("receiveMessage");
		adapter.setMessageConverter(converter);
		
		return adapter;
	}	
	
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter){
		
		LOG.info("AMQP connection factory => " + connectionFactory.getHost());
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_NAME);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	
}

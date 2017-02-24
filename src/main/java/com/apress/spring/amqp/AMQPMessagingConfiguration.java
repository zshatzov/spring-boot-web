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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPMessagingConfiguration {
	
	private static final Logger LOG = 
				LoggerFactory.getLogger(AMQPMessagingConfiguration.class);

	@Value("${journal.publish.queue}")
	private String queueName;
	
	@Value("${journal.publish.exchange}")
	private String exchange;
	
	@Autowired
	private MessageConverter converter;

	public AMQPMessagingConfiguration() {
		// TODO Auto-generated constructor stub
	}
	
	@Bean
	public Queue queue(){
		return new Queue(queueName, false);
	}
	
	@Bean
	public TopicExchange exchange(){
		return new TopicExchange(exchange, false, false);
	}
	
	@Bean
	public Binding binding(Queue queue, TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
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
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	
}

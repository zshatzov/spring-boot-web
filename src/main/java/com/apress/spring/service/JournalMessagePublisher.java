package com.apress.spring.service;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apress.spring.model.Journal;

@Service
public class JournalMessagePublisher {
	
	private static final Logger LOG = Logger.getLogger(JournalMessagePublisher.class);	
	
	@Value("${journal.publish.exchange}")
	private String exchange;
	
	@Inject
	private RabbitTemplate rabbitTemplate;
	
	@Inject
	private MessageConverter converter;

	public JournalMessagePublisher() {
		// TODO Auto-generated constructor stub
	}
	
	@Transactional
	public void publish(Journal journal){
		LOG.info(String.format("sending message to exchange => (%s)",
						exchange));
		rabbitTemplate.setMessageConverter(converter);
		rabbitTemplate.convertAndSend(exchange, "newJournal", journal);
	}

}

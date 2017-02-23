package com.apress.spring.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.apress.spring.model.Journal;

@Component
public class Receiver{
	
	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);	
	
	public void receiveMessage(Journal journal){
		LOG.info("Received new journal with id <" + journal.getId() + ">");
	}	 
}

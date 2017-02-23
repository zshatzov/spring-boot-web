package com.apress.spring.amqp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.apress.spring.model.Journal;
import com.fasterxml.jackson.databind.ObjectMapper; 

@Component
public class JournalMessageConverter implements MessageConverter {

	public JournalMessageConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Message toMessage(Object object, MessageProperties messageProperties) 
					throws MessageConversionException {
		if(!(object instanceof Journal)){
			throw new MessageConversionException("Object must be of type Journal");
		}
		
		Journal journal = (Journal) object;
		ObjectMapper json = new ObjectMapper();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();	
		
		try {
			json.writeValue(baos, journal);
		} catch (IOException e) {
			throw new MessageConversionException("Failed to serialize message", e);
		}
		
		return new Message(baos.toByteArray(), messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {		
		
		ObjectMapper json = new ObjectMapper();
		try {
			return json.readValue(message.getBody(), Journal.class);
		} catch (IOException e) {
			throw new MessageConversionException("Failed to deserialize message", e);
		} 
	}

}

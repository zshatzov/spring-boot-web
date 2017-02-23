package com.apress.spring.amqp;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.apress.spring.model.Journal;

import io.reactivex.Single;

@Component
public class Receiver{
	
	private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);	
	private BlockingQueue<Journal> queue = new LinkedBlockingQueue<>();	
	private ExecutorService executor = newSingleThreadExecutor();
 
	public Single<Journal> subscribe() { 
		return Single.create(emitter-> {
			executor.submit(()->{
				LOG.info("Check to emmit events?");
				Journal event;
				try {
					event = queue.take();
					emitter.onSuccess(event);
				} catch (InterruptedException e) {				 
				}				
			});
		}); 
	}	
	
	public void receiveMessage(Journal journal){
		LOG.info("Received new journal with id <" + journal.getId() + ">");
		queue.offer(journal);
	}	 
}

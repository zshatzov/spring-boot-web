package com.apress.spring.service;

import java.util.Arrays;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apress.spring.amqp.Receiver;
import com.apress.spring.model.Journal;
import com.apress.spring.repository.JournalRepository;

import io.reactivex.observers.DisposableSingleObserver;

@Service
public class JournalService {
	
	private JournalRepository journalRepository;
	
	@Autowired
	private Receiver receiver;
	
	private static final Logger LOG = 	
			LoggerFactory.getLogger(JournalService.class);

	public JournalService() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void setup(){
		receiver.subscribe().subscribeWith(new DisposableSingleObserver<Journal>() {
			@Override
			public void onSuccess(Journal t) {
			     LOG.info("Procssing new journal entry...");				
			}
			
			@Override
			public void onError(Throwable e) {
				LOG.error("Failed to process new journal entry", e);
			}			
		});
	}
	
	public void insertData(){
		LOG.info("Inserting journal data..."); 
		Journal journal1 = new Journal("Get to know Spring Boot","Today I will learn SpringBoot");
		Journal journal2 = new Journal("Simple Spring Boot Project","I will do my first Spring Boot Project");
		Journal journal3 = new Journal("Spring Boot Reading","Read more about Spring Boot");
		Journal journal4 = new Journal("Spring Boot in the Cloud","Spring Boot using Cloud Foundry");
		
		journalRepository.save(Arrays.asList(journal1, journal2, journal3, journal4));
		LOG.info("Inserted four journal entries."); 
	}
	
	public Stream<Journal> findAll(){
		return journalRepository.findAll().stream();
	}

	@Autowired
	public void setJournalRepository(JournalRepository journalRepository) {
		this.journalRepository = journalRepository;
	}

	public Journal add(Journal journal) { 
		return journalRepository.save(journal);
	}
}
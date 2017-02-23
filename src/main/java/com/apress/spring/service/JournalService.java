package com.apress.spring.service;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apress.spring.model.Journal;
import com.apress.spring.repository.JournalRepository;

@Service
public class JournalService {
	
	private JournalRepository journalRepository;
	
	private static final Logger LOG = 	
			LoggerFactory.getLogger(JournalService.class);
	
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

package com.apress.spring.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apress.spring.model.Journal;

//@RepositoryRestController
public interface JournalRepository extends JpaRepository<Journal, Long> {
		
	List<Journal> findByTitleContaining(//@Param("word")
			String title);
	
	List<Journal> findByCreatedAfter(//@Param("after")
			Instant date);

}

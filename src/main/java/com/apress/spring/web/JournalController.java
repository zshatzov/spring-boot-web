package com.apress.spring.web;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apress.spring.model.Journal;
import com.apress.spring.service.JournalMessagePublisher;
import com.apress.spring.service.JournalService;

@Controller
public class JournalController {
	
	private static Logger LOG = LoggerFactory.getLogger(JournalController.class);
	
	private JournalService journalService;
	
	private JournalMessagePublisher publisher;
	
	public JournalController() {
	}
	
	@RequestMapping(value="/journal", method=RequestMethod.GET)
	public String index(Model  model){
		LOG.info("Retrun list of all journals");
		List<Journal> journals = journalService.findAll().collect(toList());
		 
		model.addAttribute("journals", journals);
		
		return "journals";
	}
	
	@RequestMapping(value="/journal/new", method=RequestMethod.GET)
	public String newJournal(Model  model){
		Journal newJournal = new Journal();
		model.addAttribute("journal", newJournal);
		
		return "newJournal";
	}
	
	@RequestMapping(value= "/journal/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute Journal journal, BindingResult bindingResult){
		LOG.info("Add new journal entry...");
		if(bindingResult.hasErrors()){
			return "newJournal";
		}
		
		Journal savedJournal = journalService.add(journal);
		LOG.info("Saved journal with id => " + savedJournal.getId());
		
		publisher.publish(savedJournal);
		
		return "redirect:/journal";
	} 	
	
	@Inject
	public void setJournalService(JournalService journalService) {
		this.journalService = journalService;
	}
	
	@Inject
	public void setJournalMessagePublisher(JournalMessagePublisher publisher){
		this.publisher = publisher;
	}
}

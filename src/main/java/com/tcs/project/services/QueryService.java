package com.tcs.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.tcs.project.repository.CustomerRepository;
import com.tcs.project.repository.QueryRepository;
import com.tcs.project.resource.Claims;
import com.tcs.project.resource.Customer;
import com.tcs.project.resource.Query;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;
    @Autowired
	CustomerRepository customerrepository;
    @Autowired
    private JavaMailSender mailSender;

    public Query createQuery(Query query) {
        return queryRepository.save(query);
    }

    public List<Query> getAllQueries() {
        return queryRepository.findAll();
    }

    public Optional<Query> getQueryById(int id) {
        return queryRepository.findById(id);
    }

    public Query updateQuery(Query updatedQuery) {
        //updatedQuery.setQueryId(id);
    	Optional<Query> optional= queryRepository.findById(updatedQuery.getQueryId());
    	Query tempQuery= optional.get();
    	tempQuery.setQuestion(updatedQuery.getQuestion());
        return queryRepository.save(updatedQuery);
    }

    public void deleteQuery(int id) {
        queryRepository.deleteById(id);
    }

    public void adminAnswer(int id, String answer) {
        Optional<Query> queryOptional = queryRepository.findById(id);
        if (queryOptional.isPresent()) {
            Query query = queryOptional.get();
            query.setAnswer(answer);
            queryRepository.save(query);
            Customer customer= customerrepository.getById(query.getCustomerId());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("javafsdgroup@gmail.com");
            message.setTo(customer.getEmail());
            message.setSubject("Query Response");
            message.setText("Dear "+customer.getName()+",\n\nThank you for Connecting With us. Here is the response:\n\nQueryId: "
            		+query.getQueryId()
            		+"\nAnswer: "+query.getAnswer()
            		
                    
         
            		+"\n\n\n\n\nBest regards,\nMoon Insurance");
            
            mailSender.send(message);
            
        } else {
        	Query query = queryOptional.get();
            query.setAnswer("");
            queryRepository.save(query);
        }
    }
}


package com.kafka.Producer.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.Producer.entity.Test;
import com.kafka.Producer.entity.User;

@Service
public class KafkaService {
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
//	Sending the message to UserInfo topic
	public CompletableFuture<SendResult<String, Object>> sendMessageToTopic(User message) {
		return kafkaTemplate.send("UserInfo", message);
	}
	
//	Listen message comes from TestInfo topic
	@KafkaListener(topics = "TestInfo", groupId = "groupId")
	public void KafkaMessageListner(String message) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Test test = objectMapper.readValue(message, Test.class);
	        
	        System.out.println("Consumed Message: " + test.toString());
	        
	        System.out.println("Name: " + test.getName());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}

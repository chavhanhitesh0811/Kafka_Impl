package com.kafka.Consumer.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.Consumer.entity.Test;
import com.kafka.Consumer.entity.User;

@Service
public class KafkaService {
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
//	Sending the message to TestInfo topic
	public CompletableFuture<SendResult<String, Object>> sendMessageToTopic(Test message) {
		return kafkaTemplate.send("TestInfo", message);
	}
	
//	Listen message comes from UserInfo topic
	@KafkaListener(topics = "UserInfo", groupId = "${spring.kafka.consumer.group-id}")
	public void KafkaMessageListner(String message) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        User user = objectMapper.readValue(message, User.class);
	        
	        System.out.println("Consumed Message: " + user.toString());
	        
	        System.out.println("Name: " + user.getName());
	        System.out.println("Age: " + user.getAge());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}

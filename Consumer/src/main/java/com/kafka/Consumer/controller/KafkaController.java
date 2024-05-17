package com.kafka.Consumer.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.Consumer.entity.Test;
import com.kafka.Consumer.service.KafkaService;

@RestController
public class KafkaController {

	@Autowired
	private KafkaService kafkaService;
	
	@PostMapping("/kafkaImpl/testKafka")
	public ResponseEntity<Object>postController(@RequestBody Test user) throws InterruptedException, ExecutionException{
		CompletableFuture<SendResult<String, Object>> result = kafkaService.sendMessageToTopic(user);
		return ResponseEntity.ok(result.get().getProducerRecord().value());
	}
}

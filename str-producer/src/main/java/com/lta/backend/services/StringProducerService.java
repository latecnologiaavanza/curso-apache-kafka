package com.lta.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StringProducerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String message){
        kafkaTemplate.send("str-topic",message).whenComplete((result,ex) -> {
           if(ex != null){
               log.error("Error, al enviar el mensaje: {}",ex.getMessage());
           }
           log.info("Mensaje enviado con éxito: {}",result.getProducerRecord().value());
           log.info("Particion {}, Offset {}", result.getRecordMetadata().partition(),result.getRecordMetadata().offset());
        });
    }
}

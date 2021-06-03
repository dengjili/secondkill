/**
 * <p>Title: KafkaConsumer.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年6月2日  
 */
package com.example.secondkill.kafka.consumer;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.secondkill.service.OrderService;

/** 
 * <p>Title: KafkaConsumer.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年6月2日  
 */
@Component
public class KafkaConsumer {
	
	@Autowired
	private OrderService orderService;

    @KafkaListener(topics = {"test"})
    public void receive(String productId){
        System.out.println("test--消费消息:" + productId);
        orderService.secKill(Integer.parseInt(productId));
    }
    
    public static void main(String[] args) {
		HashMap hashMap = new HashMap();
		System.out.println(hashMap.size());
	}
}
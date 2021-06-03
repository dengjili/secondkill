/**
 * <p>Title: DemoTest1.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月25日  
 */
package com.example.secondkill;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/** 
 * <p>Title: DemoTest1.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月25日  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest1 {
	
    @Autowired
    private RedisTemplate redisTemplate;
	
	@Test
	public void test1() {
		
		ValueOperations opsForValue = redisTemplate.opsForValue();
		opsForValue.set("test", "test001", 20, TimeUnit.SECONDS);
		
		System.out.println(opsForValue.get("test"));
	}
}

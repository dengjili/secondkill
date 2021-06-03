/**
 * <p>Title: StringRedisTemplateTest.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月26日  
 */
package com.example.secondkill;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/** 
 * <p>Title: StringRedisTemplateTest.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月26日  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StringRedisTemplateTest {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void test1() {
		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		opsForValue.set("name", "zhangsan");
		
		System.out.println(opsForValue.get("name"));
	}
}

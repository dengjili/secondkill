/**
 * <p>Title: SpringConfig.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月26日  
 */
package com.example.secondkill.config;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.secondkill.zk.ZookeeperWatcher;

/** 
 * <p>Title: SpringConfig.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月26日  
 */
@Configuration
public class ZooKeeperConfig {
	
	@Value("${spring.zookeeper.addr}")
	private String zookeeperAddr;
	
	private CountDownLatch connectSemaphore = new CountDownLatch(1);
	
	@Bean
	public ZooKeeper createZooKeeper() throws Exception {
		ZookeeperWatcher watcher = new ZookeeperWatcher(connectSemaphore);
		ZooKeeper zooKeeper = new ZooKeeper(zookeeperAddr, 20000, watcher);
		watcher.setZookeeper(zooKeeper);
		// connectSemaphore.await();
		return zooKeeper;
	}
}

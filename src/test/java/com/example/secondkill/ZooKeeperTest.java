/**
 * <p>Title: ZooKeeperTest.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年5月27日  
 */
package com.example.secondkill;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/** 
 * <p>Title: ZooKeeperTest.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年5月27日  
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZooKeeperTest {
	
	@Autowired
	private ZooKeeper zooKeeper;
	
	@Test
	public void test1() throws KeeperException, InterruptedException {
		String result = zooKeeper.create("/test", "helloword".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(result);
	}
	
	@Test
	public void test2() throws KeeperException, InterruptedException {
		byte[] data = zooKeeper.getData("/test", true, new Stat());
		System.out.println(new String(data));
	}
	
	@Test
	public void test3() throws KeeperException, InterruptedException {
		zooKeeper.exists("/test", true);
		Stat setData = zooKeeper.setData("/test", "helloword".getBytes(), -1);
		System.out.println("test3..." + setData + "test3...");
	}
}

/**
 * <p>Title: ZookeeperWatcher.java</p>
 * <p>Description: </p>
 * @author    dengjili
 * @date      2021年6月1日  
 */
package com.example.secondkill.zk;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.example.secondkill.constant.Constant;
import com.example.secondkill.controller.SecKillController;

/** 
 * <p>Title: ZookeeperWatcher.java</p>  
 * <p>Description: </p>  
 * @author    dengjili
 * @date      2021年6月1日  
 */
public class ZookeeperWatcher implements Watcher {

	private ZooKeeper zooKeeper;
	
	private CountDownLatch connectSemaphore;
	
	public ZookeeperWatcher(CountDownLatch connectSemaphore) {
		this.connectSemaphore = connectSemaphore;
	}

	public void setZookeeper(ZooKeeper zooKeeper) {
		this.zooKeeper = zooKeeper;
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("启动Zookeeper Watcher.");

		// init destroy
		if (event.getType() == Event.EventType.None && event.getPath() == null) {
			System.out.println("Zookeeper初始化/销毁（init/destroy）事件类型.");
			/*
			 * try { // 创建 zookeeper 商品售罄信息根节点 String path = "/" +
			 * Constant.PRODUCT_STOCK_PREFIX; if (zooKeeper != null &&
			 * zooKeeper.exists(path, false) == null) { zooKeeper.create(path,
			 * "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			 * System.out.println("当前客户端 创建" + path + "结点."); } else {
			 * System.out.println("其他客户端 已创建" + path + "结点."); } } catch (KeeperException |
			 * InterruptedException e) { e.printStackTrace(); }
			 */
			//connectSemaphore.countDown();
		} else if (event.getType() == Event.EventType.NodeDataChanged) {
			System.out.println("Zookeeper Node Data Changed事件类型.");
			try {
				// 获取节点路径
				String path = event.getPath();
				// 获取节点数据
				String soldOut = new String(zooKeeper.getData(path, true, new Stat()));
				// 获取商品 Id
				String productId = path.substring(path.lastIndexOf("/") + 1, path.length());
				System.out.println("productId：" + productId);
				// 处理当前服务器对应 JVM 缓存
				if ("true".equals(soldOut)) {
					SecKillController.getProdocutSoldOutMap().put(Integer.parseInt(productId), true);
				} else if ("false".equals(soldOut)) {
					// 同步当前 JVM 缓存
					if (SecKillController.getProdocutSoldOutMap().containsKey(Integer.parseInt(productId))) {
						SecKillController.getProdocutSoldOutMap().remove(Integer.parseInt(productId));
					}
				}
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

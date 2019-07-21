package cn.itweknow.sbrpccorestarter.registory;

import cn.itweknow.sbrpccorestarter.exception.ZkConnectException;
import cn.itweknow.sbrpccorestarter.model.ProviderInfo;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author ganchaoyang
 * @date 2018/10/26 17:27
 * @description
 */
public class ServiceDiscovery {

    private Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);


    private volatile List<ProviderInfo> dataList = new ArrayList<>();

    public ServiceDiscovery(String registoryAddress) throws ZkConnectException {
        try {
            // 获取zk连接。
            ZooKeeper zooKeeper = new ZooKeeper(registoryAddress, 2000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    logger.info("consumer connect zk success!");
                }
            });
            watchNode(zooKeeper);
        } catch (Exception e) {
            throw new ZkConnectException("connect to zk exception," + e.getMessage(), e.getCause());
        }
    }

    public void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren("/rpc", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    // 节点改变，有服务上线或下线
                    if (event.getType().equals(Event.EventType.NodeChildrenChanged)) {
                        watchNode(zk);
                    }
                }
            });
            List<ProviderInfo> providerInfos = new ArrayList<>();
            // 循环子节点，获取服务名称
            for (String node: nodeList) {
                byte[] bytes = zk.getData("/rpc/" + node, false, null);
                String[] providerInfo = new String(bytes).split(",");
                if (providerInfo.length == 2) {
                    providerInfos.add(new ProviderInfo(providerInfo[0], providerInfo[1]));
                }
            }
            this.dataList = providerInfos;
            logger.info("获取服务端列表成功：{}", this.dataList);
        } catch (Exception e) {
            logger.error("watch error,", e);
        }
    }

    /**
     * 获取一个服务提供者
     * @param providerName
     * @return
     */
    public ProviderInfo discover(String providerName) {
        if (dataList.isEmpty()) {
            return null;
        }
        List<ProviderInfo> providerInfos = dataList.stream()
                .filter(one -> providerName.equals(one.getName()))
                .collect(Collectors.toList());
        if (providerInfos.isEmpty()) {
            return null;
        }
        return providerInfos.get(ThreadLocalRandom.current()
                .nextInt(providerInfos.size()));
    }
}

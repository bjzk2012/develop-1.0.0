![](http://kafka.apache.org/images/logo.png)
- - -
## 一、安装Kafka

* [下载](https://www.apache.org/dist/kafka/2.1.1/kafka_2.11-2.1.1.tgz)后直接解压即可

## 二、操作

> 备注：Window需要使用.\bin\windows下的.bat

* 启动zookeeper

    ```.\bin\zookeeper-server-start.sh .\config\zookeeper.properties```

* 启动kafka

    ```.\bin\kafka-server-start.sh .\config\server.properties```
    
* 创建主题
    
    ```.\bin\kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test```
    
* 查看所有主题 *
    
    ```.\bin\kafka-topics.sh --list --zookeeper localhost:2181```
    
* 查看主题详细信息 *
    
    ```.\bin\kafka-topics.sh --describe --zookeeper localhost:2181 --topic test```
     
* 查看正在同步的主题 *

    ```.\bin\kafka-topics.sh --describe --zookeeper localhost:2181 --under-replicated-partitions```
    
* 删除主题 *

    ```server.properties增加delete.topic.enable=true```
    
    ```.\bin\kafka-topics.sh --delete --zookeeper localhost:2181 --topic test```

* 创建生产者 *

    ```.\bin\kafka-console-producer.sh --broker-list localhost:9092 --topic test```
     

* 创建消费者 *

    ```.\bin\kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning```
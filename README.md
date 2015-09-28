# akka-sharding-example 

## What's this

This is a akka sharding example wrote in Java and using MongoDB to persistent. The akka sharding cluster can auto rebalance so that it can add/remove node any time. This is a project for easily understand akka sharding and check the behaviour. 

## How it works

1. Akka sharding actor will create under region
2. When Akka sharding/sharding-proxy send a message will using `MessageExtractor::shardId` to decide which region should deliver to
3. When a region received a message, region will check is the actor (base on entityId) created and is it need rebalance from another region

## How to use the example

### setup

1. install mongodb
2. change the database and collections info in common.conf

### see how the sharing works

1. run Main with n1/n1/proxy/sharding as params
2. type the commands in any vm using stdin

  - add {id} {number}
  - print {id}
  - printn {id} will print the number from node-0 to node-n
  - stop {id}

3. check the output

### try rebalance

1. start n1/proxy
2. type `printn 50` in any node 
3. start n2
4. type `printn 50` in any node
5. `print {slow-id}`

# AKKA分片示例

## 这是什么鬼

这是一个akka的分片集群示例。使用了Java API以及MongoDB作为存储。akka分片集群可以做到自动均衡，所以可以随时加减节点。这个项目可以帮助理解akka分片以及检查一些akka分片的行为。

## akka怎么这么屌

1. Akka sharding actor 会创建在 region 之下
2. 当 Akka sharding或者sharding-proxy 发送消息时，会通过 `MessageExtractor::shardId` 找到对应的 region
3. 当 region 接受到消息，会检查对应的 actor 是否已经创建以及是否需要重新负载均衡

## 怎么玩

### 准备
1. 安装 MongoDB
2. 修改 common.conf

### 看看集群怎么工作

1. 使用Main+参数（n1/n1/proxy/sharding）启动多个节点
2. 输入命令

  - add {id} {number}
  - print {id}
  - printn {id} will print the number from node-0 to node-n
  - stop {id}

3. 检查各个节点的输出

### 尝试rebalance

1. 启动 n1/proxy
2. 输入 `printn 50`
3. 启动 n2
4. 输入 `printn 50` 
5. 尝试一个需要长时间恢复的节点`print {slow-id}`


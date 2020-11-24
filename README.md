### 框架设计思路

-只有gateway端口堆外暴露，所有api request经过gateway转发,gateway中设定了路由规则，并进行登录认证和安全认证，gateway由Spring Cloud GateWay实现

-spring cloud eureka作为服务的注册中心，feign进行服务调用

-用mysql数据库存储user，device，好友关系等强关联且单条数据所占存储较少的数据，用Drudid作为数据库连接池，提供了丰富的功能，有dashboard，可以监控慢查询等，用jpa作为ORM框架

-用mongodb和elasticsearch存储文档类数据， 其中文章和动态由于要提供搜索功能需要用elasticsearch存储，用户发表的内容全部用mongodb存储

-elasticsearch配置了elasticsearch head提供了可视化web页面，并添加了IK分词器，用于中文分词

-使用twitter的snow shake算法生成long类型的ID

-使用NamedEntityGraph对jpa的关联查询进行优化，避免懒加载带来的性能问题

-使用redis作为缓存，有两个redis连接连接，分别用于对象类型和集合类型，对象类型包括string和hash，集合类型包括list,set和zset，集合类型可以用于实现多种业务功能

-对象存储服务器

-rabbitmq用于异步消息的传递，使用死信队列处理聊天室过期等业务逻辑

-xxl-job作为任务调度器，实现定时的消息推送等功能

-netty作为data center和node server的通讯框架，data center 和 node server的介绍见架构设计

-zookeeper作为data center和node server的注册中心，node server和data center的监控中心互相监听对方节点

-客户端长连接消息的加密仿照https协议实现（待定，开发测试中为了方便，不采取加密）

-jwt实现单点登录

-使用jackson作为序列化和反序列化工具



### 开发环境配置
docker: mysql:5.7, mongo:latest(4.2), redis:6.0.5 ,rabbitmq:management
zookeepr 1~3
elasticserach 7 + elasticserach head + ik分词器插件

需要启动的项目:eureka server， xxl-job-admin

mongodb管理工具：mongo campass
redis管理工具：another redis desktop management



### 学习技能和知识
1.netty：https://github.com/netty/netty https://netty.io/wiki/user-guide-for-4.x.html

2.zookeeper: https://zookeeper.apache.org/ https://www.jianshu.com/p/f5cbe7b6ef73

3.redis: https://redis.io/

4.jpa: https://github.com/wenhao/jpa-spec 大部分jpa的工具类模仿了jpa specification

5.mongodb: https://www.runoob.com/mongodb/mongodb-tutorial.html

6.elasticsearch: https://www.elastic.co/ https://www.elastic.co/cn/elasticsearch/

7.xxl-job: https://www.xuxueli.com/xxl-job

8.docker: https://docs.docker.com/

9.Spring Cloud: https://spring.io/projects/spring-cloud

10.https

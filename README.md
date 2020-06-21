read.me
Zookeeper 集群配置：
3台
conf目录下复制粘贴zooconfig-sample,文件名改为zoo.cfg

第一台更改配置如下：
dataDir=d:\\zookeeper1\\data
dataLogDir=d:\\zookeeper1\\log  

设置client地址 2181-2183

末尾添加：
server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2887:3887
server.3=127.0.0.1:2889:3889

dataDir目录下新建myid文件 输入1

其余两台依次类推。

启动3个zookeeper server和client。





异常信息

```
2019-08-10 22:11:03.052 ERROR 35257 --- [           main] com.alibaba.dubbo.qos.server.Server      :  [DUBBO] qos-server can not bind localhost:22222, dubbo version: 2.6.7, current host: 192.168.1.4

java.net.BindException: Address already in use
	at java.base/sun.nio.ch.Net.bind0(Native Method) ~[na:na]
	at java.base/sun.nio.ch.Net.bind(Net.java:455) ~[na:na]
	at java.base/sun.nio.ch.Net.bind(Net.java:447) ~[na:na]
	at java.base/sun.nio.ch.ServerSocketChannelImpl.bind(ServerSocketChannelImpl.java:219) ~[na:na]
	at io.netty.channel.socket.nio.NioServerSocketChannel.doBind(NioServerSocketChannel.java:132) ~[netty-all-4.1.38.Final.jar:4.1.38.Final]
	at io.netty.channel.AbstractChannel$AbstractUnsafe.bind(AbstractChannel.java:551) ~[netty-all-4.1.38.Final.jar:4.1.38.Final]
	at io.netty.channel.DefaultChannelPipeline$HeadContext.bind(DefaultChannelPipeline.java:1345) ~[netty-all-4.1.38.Final.jar:4.1.38.Final]

```

原因：
```
Qos=Quality of Service，qos是Dubbo的在线运维命令，可以对服务进行动态的配置、控制及查询
默认端口22222
本地启动了服务提供者和消费者，就导致后启动的端口绑定失败
```
| 参数               | 说明              | 默认值 |
| ------------------ | ----------------- | ------ |
| qosEnable          | 是否启动QoS       | true   |
| qosPort            | 启动QoS绑定的端口 | 2222   |
| qosAcceptForeignIp | 是否允许远程访问  | false  |

这个地方我们将Qos禁用

```shell
-Ddubbo.application.qos.enable=false
```


# 布鲁斯网关——基于 API 接口开放平台的自研微服务网关
## 为什么要开发这个项目？
之前和同学有过一个关于 API 接口开放平台的项目的开发，其中涉及到了 API 签名认证的功能，这里简单介绍一下其实现的步骤：
1. 生成密钥对：给每个用户生成唯一的密钥对（accessKey 和 secretKey），并保存到数据库中，仅用户本人可查看自己的密钥对。
2. 请求方生成签名： 请求方（客户端）使用 secretKey 对请求参数和数据进行签名，签名的内容包括请求参数、时间戳、随机数等，签名加密算法此处选择 MD5。
3. 请求方发送请求：请求方将请求参数、签名、用户标识一起发送给 API 提供者，通常会把签名等元信息放到请求头参数中传递，注意千万不要传递 secretKey。
4. API 提供者验证签名：在 API 网关中，通过请求头获取到用户标识，根据标识到数据库中查找该用户对应的 accessKey 和 secretKey，并使用相同的签名算法生成签名，和请求中的签名进行比对，如果签名一致，则 API 提供者可以信任请求方，可以进行后续操作。
   
然后这里使用 SpringCloud GateWay 作为网关，然后在网关处进行统一鉴权认证，基于这种成熟的框架开发的好处在于其支持快速整合和部署，并且由于其经过了⻓时间的市场检验，稳定性也有⼀定的保证。

但是使用了这个虽然方便，可以实现快速开发的效果，但是当你遇到问题的时候，你就只能花时间从头去了解和排查问题，这在一定程度上增加了项目的开发成本，其就像一个透明的盒子，可以帮你快速解决一些麻烦，但是如果遇到问题了，你就需要自己去打开这个盒子，去了解一些之前没有了解过的细节。

并且，其过于成熟稳定可能也就意味着其在设计的时候可能考虑了很多的东西，即可能把一些当前项目不需要的功能整合进了系统中，导致系统功能可能过于冗余，那么这个时候，在自己的需求上去一开一个适应自己需求的网关就非常重要了。



## 市场调研
1. **Spring Cloud Gateway**

-   优点：基于 Spring 框架，拥有庞大的 Spring 生态系统，可以轻松集成其他组件。支持多种协议，如WebSocket和HTTP/2。

-   缺点：相对于其他选择，性能较慢。没有像Kong那样的灵活性，其生态和社区支持较弱。

-   设计侧重点：集成多种协议和插件，扩展性。
2. **Kong**

-   优点：基于 Nginx  和 OpenResty，提供丰富的插件，管理界面较为友好。

-   缺点：⾼性能场景可能需优 化配置，插件⽣态不如 Apache/Nginx。

-   设计侧重点：扩展性和插件生态系统。
3. **Nginx**

-   优点：高性能，配置灵活，轻量级，高稳定性。

-   缺点：模块化程度较低，扩展性差，异步处理能力受限。

-   设计侧重点：⾼性能HTTP服务器和 反向代理。
   
4. **Apache HTTP Server**
-   优点：模块丰富，社区活 跃，跨平台，⽂档⻬全 。

-   缺点：性能较差，配置复杂，更重量级。

-   设计侧重点： 多功能 Web 服务器，重视模块化。
5. **HAProxy**

- 优点：高性能，支持 TCP 和 HTTP 代理，稳定且成熟。

-   缺点：配置不如 Nginx 直观，缺乏现代 Web 界面。

-   设计侧重点：专注于高并发连接的负载均衡。
6. **Traefik**

-   优点：针对容器和云环境设计，支持多种协议和场景，易于配置和部署。可以自动发现新的服务，支持自动SSL证书管理，具有良好的扩展性。

-   缺点：社区较新，历史较短，在某些高级用例下，性能可能不如其他网关。

-   设计侧重点：云原生环境中的动态配置。
   
所以，在调查了一些比较成熟且知名度比较高的网关之后，整理出了设计一个网关的侧重点：
1. **性能与可伸缩性**
- 关注高吞吐量和低延迟处理，以便于能够处理大量并发连接和数据流。
- 设计可在多个服务器、数据中⼼或地理区域之间伸缩的解决⽅案。
2. **安全性**
- 实现⾼级安全特性，如SSL/TLS终⽌、OAuth、JWT、API密钥验证和防⽌DDoS攻击等。
- 确保所有网关的流量都符合最新的安全标准和法规要求。
3. **可观测性**
- 提供详细的监控和日志记录功能，使得运维团队可以观测和诊断问题。
- 集成现有监控⼯具和警报系统的能⼒。
4. **路由能力**
- 开发动态路由和负载均衡策略，以⽀持微服务架构中服务发现的需求。
- ⽀持基于URL、路径或头部的路由决策。
5. **扩展性**
- 构建插件架构，使新功能能够以模块化的⽅式添加。
- 保持核⼼轻量级，允许通过插件或服务集成额外功能。
6. **多协议支持**
- 考虑⽀持多种⽹络协议，不仅限于HTTP/HTTPS，也包括WebSocket、RPC等。
7. **高可用性**
- 确保⽹关设计能够容忍单点故障和⽹络分区，提供故障转移和灾难恢复机制。

在列举出以上要解决的这些点以后，我们现在可以开始进行分析，即逐步解决以上这些问题：
1. **性能与可伸缩性**
- 使⽤ **Netty** 进⾏异步⽹络编程，Netty 是⼀个⾼性能的⽹络应⽤程序框架，可以处理⼤量的并发连接。
- 使用通过**缓存**：通过使用 Redis、Caffeiene 等缓存的形式来减少数据库的访问频率，从而提高性能。
- 提高异步处理能力：使用 **Disruptor** 这个高性能的异步事件处理框架，实现高效的事件发布以及消费，提高并发处理能力。
2. **安全性**
- 集成 JWT ⽤于安全的API访问。
- 利⽤ TLS/SSL 加密传输数据。
3. **可观测性**
- 集成 **Micrometer 或 Dropwizard Metrics** 来收集和导出性能指标。
- 使⽤ **ELK Stack**（Elasticsearch, Logstash, Kibana）来收集和分析⽇志数据。
- 利⽤ **Prometheus 和 Grafana** 进⾏监控和警报。
4. **路由能力**
- 利⽤ **Zuul** 或⾃定义的 **Servlet Filters** 进⾏动态路由。
- 结合 **Consul** 或 Eureka 或 Nacos 进⾏服务发现和注册。
5. **扩展性**
- 设计插件架构，使得可以通过 Java SPI (Service Provider Interface) 加载新模块。
6. **多协议支持**
- 使⽤ gRPC/Dubbo 来⽀持RPC调⽤。
- ⽀持 WebSocket ⽤于双向通信，使⽤Java的 JSR 356 或 Spring Framework 的WebSocket API。

7. **高可用性**
- 使⽤ Nacos / ZooKeeper / etcd 来管理⽹关的配置信息和服务元数据，以⽀持⾼可⽤部署。

好的，那么其实基于上⾯的分析，我们就已经可以⼤致的得到我们设计⼀个⽹关所需要的⼀些技术上的⽅向了，接下来的就是确定这些技术，并且确定⾃⼰设计该⽹关时的⼀个架构图了。

## 技术栈思考
### 性能与可伸缩性
参考⽬前主流的⽹关的设计，有SpringCloud Gateway以及Zuul，他们的底层都⼤量使⽤了异步编程的思想，并且也都有⾮常重要的⽹络通信上的设计.

且由于我们的网关采用自研的方式，其本身也是一个单独的服务，所以并不需要使用到 Spring Boot 这种框架，因此我们可以直接使用原生 Java 框架来进行编写代码。且 SpringCloud Gateway 底层也是大量使用到了 Netty，所以我们这里可以考虑一下也是用 Netty 来进行编写。

缓存以及高性能这块，我们分布式缓存直接使用 Redis 来进行实现，然后本地缓存可以使用 Caffeine，因为这两个 Redis 是市面上使用最为广泛的缓存中间件，而 Caffeine 更是有着本地缓存之王的称号，所以选择这两个框架主要是看中了其成熟的特点。

然后为了提高其缓冲区的性能，这里考虑使用 Disruptor 这个无锁队列，因为其无界队列的特性，可以将其作为缓冲区队列提升性能。
### 安全性
我们使⽤JWT，其优点在于简单的Token格式，便于跨语⾔和服务传递，适合于微服务和分布式系统的安全性设计。

当然缺点也在于我们需要精细的管理和保护我们的密钥。

这⾥并不打算⽀持TLS/SSL，因为作为个⼈开发者，想要去⽀持TLS/SSL是⽐较复杂的，并且还需要管理证书的⽣命周期，会影响项⽬开发的进度，因此并不打算在我的⽹关中⽀持TLS/SSL。

### 可观测性
- Micrometer 和 Dropwizard Metrics
  - 优点: 两者都是成熟的度量收集框架，提供了丰富的度量集合和报告功能。
  - 缺点：可能需要适配特定的监控系统或标准。
- ELK Stack
   -  优点: 提供了⼀个完整的⽇志分析解决⽅案，适⽤于⼤规模⽇志数据的收集、搜索和可视化
   -  缺点: 组件较多，搭建和维护相对复杂。
- Prometheus 和 Grafana:
   - 优点: ⾼度适合于时序数据监控，Grafana 提供强⼤的数据可视化。
   - 缺点: 需要配置和维护Prometheus数据抓取和存储。

这里我们选择使用最后一种，因为目前市面上最为流行的也是最后一种，其相对于其他两种更加简单易用一些。

### 路由能力 / 高可用
同时，在上⽂也提到了，⽹关是需要⽤到注册中⼼的，因为我们的请求具体最后要转发到那个路由，是需要从注册中⼼中拉取服务信息的，⽬前注册中⼼有：Zookeeper，Eureka，Nacos，Apollo，etcd，Consul

他们各有优劣势，⽐如Zookeeper保证的是CP⽽不是AP，我们知道，⽹关是应⽤的第⼀道⻔户，我们使⽤Dubbo的时候会使⽤Zookeeper ，但是对于⽹关，可⽤性⼤于⼀致性，因此Zookeeper我们不选。

⽽Eureka都和SpringCloud⽣态有⽐较紧密的联系，因此如果我们使⽤它，就会增加我们的⽹关和 SpringCloud 的耦合，不太符合我们⾃研的初衷，所以也不选。

Etcd虽然是通⽤的键值对分布式存储系统，可以很好的应⽤于分布式系统，但是依旧没有很好的优势，虽然他很轻量级，但是相对于 Nacos 来说，其并没有很明显的优势，这⾥暂不考虑，Consul和Etcd差不多，所以这⾥也不考虑Consul。

这⾥选⽤Nacos作为注册中⼼，Nacos⾸先⽀持CP和AP协议，并且提供了很好的控制台⽅便我对服务进⾏管理。同时，Nacos的社区相对来说⾮常活跃，⽹络上的资料也更加的多，Nacos使用范围更广，因此在这⾥选择Nacos作为注册中⼼。

当然，上⾯的⼏种注册中⼼都可以使⽤，没有特别明显的优劣势，他们也都有各⾃合适的场合，具体场合具体分析，主要是要分析⾃⼰的团队更加了解或者适合哪⼀种注册中⼼。

⽽配置中⼼⽅⾯，有SpringCloud Config，Apollo，Nacos，这⾥很明显，依旧选择Nacos，因为Nacos不仅仅是注册中⼼也是配置中⼼。因此选⽤Nacos我们可以减少引⼊不必要的第三⽅组件。

### 多协议支持
可以考虑的有gRPC和Dubbo，gRPC⽀持多种语⾔，并且基于HTTP/2.0，Dubbo在Alibaba使⽤的⽐较多，并且⽐较适合Java的⽣态。同时gRPC的使⽤要求熟悉Protobuf，所以这⾥为了减少成本，考虑使⽤Dubbo。

所以，经过分析，可以得出最终的技术栈如下：
1. 开发语言： Java （JDK 19）
2. 网络通信框架：Netty 4.1.51
3. 缓存：Redis、Caffeine 版本不限
4. 注册中心与配置中心：Dubbo 2.7.x
5. 日志监控：Prometheus、Grafana 版本不限
6. RPC 协议：Dubbo 2.7.x
7. 安全鉴权：JWT 版本不限


## 项目架构图
基于以上分析之后，我们可以画出以下架构图：
![image](https://github.com/DIDA-lJ/BlossomGateWay/assets/97254796/d9cb1e54-1ee0-4c24-b333-acd27e1f69b0)

- Common：维护公共代码，⽐如枚举
- Client：客户端模块，⽅便我们其他模块接⼊⽹关
- Register Center：注册中⼼模块
- Config Center：配置中⼼模块
- Container：包含核⼼功能
- Context：请求上下⽂，规则
- FilterChain：通过责任链模式，链式执⾏过滤器
- FlowFilter：流控过滤器
- LoadBalanceFilter：负载均衡过滤器
- RouterFilter：路由过滤器
- TimeoutFilter：超时过滤器
- OtherFilter：其他过滤器
- NettyHttpServer：接收外部请求并在内部进⾏流转
- Processor：后台请求处理
- Flusher：性能优化
- MPMC：性能优化
- SPI Loader：扩展加载器
- Plugin Loader：插件加载器
- Dynamic Loader：动态配置加载器
- Config Loader：静态配置加载器


## 网关处理流程
根据上⾯的分析，我们可以得到如下的⼀个处理流程。
![image](https://github.com/DIDA-lJ/BlossomGateWay/assets/97254796/5274487c-760a-4525-8ddc-e10bfb276222)

## 开发文档
项目具体细节可以查看开发文档（语雀中查看）
<a href="https://www.yuque.com/zeovo-10k9s/lqwlrb/qyhycrlgwgvtag2a?singleDoc# 《BlossmGateWay 网关技术文档》">
项目开发文档
</a>




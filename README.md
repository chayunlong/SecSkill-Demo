该代码实现了一个简单的秒杀系统，具有以下功能：
    1.登录功能：密码采用M5加密，同时每一个用户对应一个Session存储在Redis中，方便下次登录
    2.商品展示功能：能够从数据库获取商品的信息，并进行一个展示
    3.秒杀详情页：记录秒杀商品的详细信息，该页面具有商品的秒杀时间、秒杀价格、秒杀倒计时等信息
    4.订单页面：秒杀成功后，展示订单的信息（还未充分完成，比较简陋）

该项目用到的技术：
    1.MyBatis-Plus：是一个方便操作数据库的代码工具，其中有一些方法可以直接对数据库进行操作，前提是连接到数据库
XML文件就是sql语句的文件，与数据库进行交互 mapper文件夹下的Mapper接口中定义接口，在资源下的Mapper的相应的Mapper.xml
文件中实现 sql语句  对数据库的操作。
    ``
    #Mybatis-plus配置
    mybatis-plus:
    #??Mapper.xml文件的位置
    mapper-locations: classpath*:/mapper/*Mapper.xml
    #??Mybatis??????????????????
    type-aliases-package: com.yunlong.seckilldemo.pojo
    ``
    2.Redis技术：对Session进行缓存 将用户Session存储到Redis中，减少对数据库的查询 优化速度，同时可以将商品的库存导入到
Redis中，在其中进行库存预减，优化系统
``
#redis配置
redis:
#服务器地址
host: 192.168.32.128
port: 6379
#默认数据库
database: 0
#超时时间
timeout: 10000ms
lettuce:
pool:
#最大连接数 默认8
max-active: 8
#最大连接阻塞等待时间 默认-1 （永久）
max-wait: 10000ms
#最大空闲连接 默认 8
max-idle: 200
#最小空闲连接 默认0
min-idle: 5
``
    3.thymeleaf模板引擎技术：对页面进行渲染和排版
    4.数据源配置：连接到数据库
``
#数据源配置
datasource:
driver-class-name: com.mysql.cj.jdbc.Driver
url: jdbc:mysql://localhost:3306/seckill?useUnicode=true&characterEncoding=UTF-8
username: root
password: 123456
hikari:
#连接池
pool-name: DateHikariCP
#空闲时最小连接数
minimum-idle: 5
#空闲连接超时时间 默认600000（10分中国）
idle-timeout: 600000
#连接池中允许的最大连接数 默认10
maximum-pool-size: 10
#开启自动提交
auto-commit: true
#一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟
max-lifetime: 1800000
#等待连接池分配连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
connection-timeout: 30000
#数据库连接测试语句
connection-test-query: SELECT 1
``
    5.RabbitMQ消息中间件：消息的转发：
``
rabbitmq:
#主机ip
host: 192.168.32.129
username: guest
password: guest
#虚拟主机
virtual-host: /
#端口
port: 5672
listener:
simple:
#消费者最小数量
concurrency: 10
#消费者最大数量
max-concurrency: 10
#限制消费者只处理一条消息 处理完再处理下一条消息
prefetch: 1
#启动时是否默认启动容器 默认true
auto-startup: true
#被拒绝时 重新进入队列
default-requeue-rejected: true
template:
retry:
#发布重试 默认为false
enabled: true
#重试时间 默认为1000ms
initial-interval: 1000ms
#最大重试次数 默认3次
max-attempts: 3
#重试最大时间间隔 默认10000ms
max-interval: 10000ms
#重试间隔乘数:比如2.0 第一次等10s 第二次等20s 第三次就等40s
multiplier: 1
``

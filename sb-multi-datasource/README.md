在实际的项目（特别是一些较大的项目）的开发过程中我们会经常遇到本篇文章将通过一个具体的例子来讲解如何在SpringBoot+Mybatis+Druid+MySQL项目中实现多数据源的配置。
### 准备
> 一个springboot项目，可以去官网快速生成一个。  
> 一个可用的MySQL数据库。

### 数据库方面的准备工作
在本例中我们将会新建两个数据库（dbone和dbtwo）,其中dbone中拥有一张city表，dbtwo中包含一张user表。  
* 新建数据库

```
-- 创建数据库
CREATE DATABASE dbone;
CREATE DATABASE dbtwo;
```

* dbone，建city表并插入一条数据

```
-- 在dbone中新建city表
USE dbone;
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
`id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市编号',
`province_id` int(10) unsigned NOT NULL COMMENT '省份编号',
`city_name` varchar(25) DEFAULT NULL COMMENT '城市名称',
`description` varchar(25) DEFAULT NULL COMMENT '描述',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT city VALUES (1 ,1,'上海市','直辖市');
```

* 在dbtwo中新建user表

```
-- 初始化dbtwo的表
USE dbtwo;
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `user_name` varchar(25) DEFAULT NULL COMMENT '用户名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  `city_id` int(11) DEFAULT NULL COMMENT '城市id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT user VALUES (1 ,'名字想好没。', '个人主页：https://itweknow.cn', 1);
```

### 项目依赖
在准备阶段我们就已经生成了一个SpringBoot的一个基础的web项目，如果你不是很清楚怎么生成的话，可以看我[之前的文章](https://itweknow.cn/detail?id=36)。前面也提到过我们的例子使用的技术栈是SpringBoot+MySQL+MyBatis+Druid，所以会有以下的依赖，我们加在自己的pom.xml中就可以了。
pom.xml
```
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>

<!--连接驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>

<!--druid数据源-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.0.28</version>
</dependency>
```

### 多数据源的配置
* 配置文件中添加数据源配置项  
我们需要在application.propertis中加入多数据源的配置项
```
## dbone 数据源配置
dbone.druid.datasource.url=jdbc:mysql://localhost:3306/dbone?serverTimezone=GMT%2B8
dbone.druid.datasource.username=root
dbone.druid.datasource.password=123456
dbone.datasource.driverClassName=com.mysql.jdbc.Driver

## dbtwo 数据源配置
dbtwo.druid.datasource.url=jdbc:mysql://localhost:3306/dbtwo?serverTimezone=GMT%2B8
dbtwo.druid.datasource.username=root
dbtwo.druid.datasource.password=123456
dbtwo.datasource.driverClassName=com.mysql.jdbc.Driver
```
* DruidDataSourceProperties  
我们抽象了一个类来存储创建一个数据源所需要的属性，每个数据源都有一个自己Properties类继承自DruidDataSourceProperties，并利用@ConfigurationProperties注解来从配置文件中读取各个配置项的值。由于文章篇幅的原因这里就不贴代码了，需要的话可以在源码中找到（在cn.itweknow.springbootmultidatasource.config包下）。

* 主数据源dbone的配置

```
@Configuration
@EnableConfigurationProperties({ DbOneDruidDataSourceProperties.class })
// 指定在哪些包下扫描Mapper
@MapperScan(value = { "cn.itweknow.springbootmultidatasource.dao.mapper.dbone" }, sqlSessionFactoryRef = "dbOneSqlSessionFactory")
@ConditionalOnProperty(name = "dbone.druid.datasource.url", matchIfMissing = false)
public class DbOneDruidDataSourceConfiguration {

    /**
     * 指定mapper.xml文件的地址
     */
    static final String MAPPER_LOCATION = "classpath:sqlmap/dbone/*Mapper.xml";

    @Autowired
    private DbOneDruidDataSourceProperties dbOneDruidDataSourceProperties;

    @Bean(name = "dbOneDruidDataSource", initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean(name = "dbOneDruidDataSource")
    @Primary
    public DruidDataSource dbOneDruidDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbOneDruidDataSourceProperties.getDriverClassName());
        dataSource.setUrl(dbOneDruidDataSourceProperties.getUrl());
        dataSource.setUsername(dbOneDruidDataSourceProperties.getUsername());
        dataSource.setPassword(dbOneDruidDataSourceProperties.getPassword());
        return dataSource;
    }

    @Bean(name = "dbOneTransactionManager")
    @ConditionalOnMissingBean(name = "dbOneTransactionManager")
    @Primary
    public DataSourceTransactionManager dbOneTransactionManager(@Qualifier("dbOneDruidDataSource") DruidDataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

    @Bean(name = "dbOneTransactionTemplate")
    @ConditionalOnMissingBean(name = "dbOneTransactionTemplate")
    @Primary
    public TransactionTemplate dbOneTransactionTemplate(@Qualifier("dbOneTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "dbOneSqlSessionFactory")
    @ConditionalOnMissingBean(name = "dbOneSqlSessionFactory")
    @Primary
    public SqlSessionFactory dbOneSqlSessionFactory(@Qualifier("dbOneDruidDataSource") DruidDataSource druidDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(druidDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
```

* 从数据源dbtwo的配置同主数据源的配置大同小异，主要的区别是Mapper的扫描包、mapper.xml不同，以及从数据源的配置没有@PrimaryZ注解。

### 测试代码的编写

本例实现的逻辑是从dbtwo中查询出一条用户数据，然后根据该用户的cityId字段去dbone中查询出对与的城市数据。最后将从两个库中查询出的数据组合起来返回给调用方。具体实现的代码可以参考本例源码，这里就只粘一下项目的结构，并稍作解释。

```
├── multi-datasource.sql
├── pom.xml
├── README.md
├── springboot-multi-datasource.iml
└── src
    └── main
        ├── java
        │   └── cn
        │       └── itweknow
        │           └── springbootmultidatasource
        │               ├── config                                                   // 数据源的配置
        │               │   ├── DbOneDruidDataSourceConfiguration.java
        │               │   ├── DbOneDruidDataSourceProperties.java
        │               │   ├── DbTwoDruidDataSourceConfiguration.java
        │               │   ├── DbTwoDruidDataSourceProperties.java
        │               │   └── DruidDataSourceProperties.java
        │               ├── controller                                              // controller
        │               │   └── UserController.java
        │               ├── dao                                                     // 持久层，实体类
        │               │   ├── City.java
        │               │   ├── example                                             // mybatis example
        │               │   │   ├── dbone
        │               │   │   │   └── CityExample.java
        │               │   │   └── dbtwo
        │               │   │       └── UserExample.java
        │               │   ├── mapper                                              // mybatis mapper接口
        │               │   │   ├── dbone
        │               │   │   │   └── CityMapper.java
        │               │   │   └── dbtwo
        │               │   │       └── UserMapper.java
        │               │   └── User.java
        │               ├── repository                                              
        │               │   ├── CityRepository.java
        │               │   └── UserRepository.java
        │               ├── service                                                 // 业务逻辑
        │               │   ├── impl
        │               │   │   └── UserServiceImpl.java
        │               │   └── UserService.java
        │               ├── SpringbootMultiDatasourceApplication.java
        │               └── vo
        │                   └── User.java
        └── resources
            ├── application.properties
            └── sqlmap                                                              // mapper.xml
                ├── dbone
                │   └── CityMapper.xml
                └── dbtwo
                    └── UserMapper.xml
```

想要源码吗？[戳这里！！！](https://github.com/ganchaoyang/blog/tree/master/springboot-multi-datasource)

package cn.itweknow.sbelkstart.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableConfigurationProperties({ DbOneDruidDataSourceProperties.class })
@MapperScan(value = { "cn.itweknow.sbelkstart.db.mapper" }, sqlSessionFactoryRef = "sqlSessionFactory")
@ConditionalOnProperty(name = "dbone.druid.datasource.url", matchIfMissing = false)
public class DbOneDruidDataSourceConfiguration {

    /**
     * mapper.xml文件的地址
     */
    static final String MAPPER_LOCATION = "classpath:sqlmap/*Mapper.xml";

    @Autowired
    private DbOneDruidDataSourceProperties dbOneDruidDataSourceProperties;

    @Bean(name = "druidDataSource", initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean(name = "druidDataSource")
    @Primary
    public DruidDataSource dbOneDruidDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbOneDruidDataSourceProperties.getDriverClassName());
        dataSource.setUrl(dbOneDruidDataSourceProperties.getUrl());
        dataSource.setUsername(dbOneDruidDataSourceProperties.getUsername());
        dataSource.setPassword(dbOneDruidDataSourceProperties.getPassword());
        return dataSource;
    }

    @Bean(name = "transactionManager")
    @ConditionalOnMissingBean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager dbOneTransactionManager(@Qualifier("druidDataSource") DruidDataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

    @Bean(name = "transactionTemplate")
    @ConditionalOnMissingBean(name = "transactionTemplate")
    @Primary
    public TransactionTemplate dbOneTransactionTemplate(@Qualifier("transactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory dbOneSqlSessionFactory(@Qualifier("druidDataSource") DruidDataSource druidDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(druidDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}
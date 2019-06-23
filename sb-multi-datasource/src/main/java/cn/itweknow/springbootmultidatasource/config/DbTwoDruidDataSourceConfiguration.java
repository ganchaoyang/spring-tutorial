package cn.itweknow.springbootmultidatasource.config;

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

/**
 * @author ganchaoyang
 * @date 2019/1/22 18:45
 * @description
 */
@Configuration
@EnableConfigurationProperties({ DbTwoDruidDataSourceProperties.class })
@MapperScan(value = { "cn.itweknow.springbootmultidatasource.dao.mapper.dbtwo" }, sqlSessionFactoryRef = "dbTwoSqlSessionFactory")
@ConditionalOnProperty(name = "dbtwo.druid.datasource.url", matchIfMissing = false)
public class DbTwoDruidDataSourceConfiguration {

    /**
     * mapper.xml文件的地址
     */
    static final String MAPPER_LOCATION = "classpath:sqlmap/dbtwo/*Mapper.xml";

    @Autowired
    private DbTwoDruidDataSourceProperties dbTwoDruidDataSourceProperties;

    @Bean(name = "dbTwoDruidDataSource", initMethod = "init", destroyMethod = "close")
    @ConditionalOnMissingBean(name = "dbTwoDruidDataSource")
    public DruidDataSource dbTwoDruidDataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dbTwoDruidDataSourceProperties.getDriverClassName());
        dataSource.setUrl(dbTwoDruidDataSourceProperties.getUrl());
        dataSource.setUsername(dbTwoDruidDataSourceProperties.getUsername());
        dataSource.setPassword(dbTwoDruidDataSourceProperties.getPassword());
        return dataSource;
    }

    @Bean(name = "dbTwoTransactionManager")
    @ConditionalOnMissingBean(name = "dbTwoTransactionManager")
    public DataSourceTransactionManager dbTwoTransactionManager(@Qualifier("dbTwoDruidDataSource") DruidDataSource druidDataSource) {
        return new DataSourceTransactionManager(druidDataSource);
    }

    @Bean(name = "dbTwoTransactionTemplate")
    @ConditionalOnMissingBean(name = "dbTwoTransactionTemplate")
    public TransactionTemplate dbTwoTransactionTemplate(@Qualifier("dbTwoTransactionManager") PlatformTransactionManager platformTransactionManager) {
        return new TransactionTemplate(platformTransactionManager);
    }

    @Bean(name = "dbTwoSqlSessionFactory")
    @ConditionalOnMissingBean(name = "dbTwoSqlSessionFactory")
    public SqlSessionFactory dbTwoSqlSessionFactory(@Qualifier("dbTwoDruidDataSource") DruidDataSource druidDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(druidDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

}

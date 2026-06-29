//package com.smc.smccloud.config.mybatisplus;
//
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
//
///**
// * @Author lyc
// * @Date 2023/1/3 15:35
// * @Descripton TODO
// */
//@Configuration
//@MapperScan(basePackages = {"com.smc.smccloud.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
//public class DBConfiguration {
//
//    @Primary
//    @Bean(name = "dataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource gfDataSource() {
//        // DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
//        // return dataSource;
//        return DataSourceBuilder.create().type(DruidDataSource.class).build();
//    }
//
//    @Primary
//    @Bean(name = "sqlSessionFactory")
//    public SqlSessionFactory gfSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//
//        factoryBean.setDataSource(dataSource);
//        // factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
//
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setDefaultStatementTimeout(1); // 设置sql执行超时时间：：（秒）
//        factoryBean.setConfiguration(configuration);
//        SqlSessionFactory sqlSessionFactory = factoryBean.getObject();
//        return sqlSessionFactory;
//    }
//
//    @Primary
//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager gfTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Primary
//    @Bean(name = "sqlSessionTemplate")
//    public SqlSessionTemplate gfSqlSessionTemplate(
//            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
//        return sqlSessionTemplate;
//    }
//
//
//}

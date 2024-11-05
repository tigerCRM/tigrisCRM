//package com.example.crm.core.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = "com.example.crm.repository.mapper")
//public class DatabaseConfig {
//
//	@Autowired private Environment env;
//
//	@Bean(destroyMethod = "close")
//	public DataSource dataSource() throws Exception {
//
//		HikariDataSource dataSource = new HikariDataSource();
//		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
//		dataSource.setUsername(env.getProperty("jdbc.username"));
//		dataSource.setPassword(env.getProperty("jdbc.password"));
//		dataSource.setMaximumPoolSize(env.getProperty("jdbc.max.poolSize", Integer.class));
//		dataSource.setConnectionTestQuery(env.getProperty("jdbc.testQuery"));
//
//		Properties dsProperties = new Properties();
//		dsProperties.put("cachePrepStmts", true);
//        dsProperties.put("prepStmtCacheSize", 250);
//        dsProperties.put("prepStmtCacheSqlLimit", 2048);
//        dsProperties.put("useServerPrepStmts", true);
//
//		dataSource.setDataSourceProperties(dsProperties);
//
//		return dataSource;
//	}
//
//	@Bean
//	public VendorDatabaseIdProvider databaseIdProvider() {
//
//		Properties properties = new Properties();
//		properties.put("SQL Server", "sqlserver");
//		properties.put("DB2", "db2");
//		properties.put("Derby", "derby");
//		properties.put("Oracle", "oracle");
//		properties.put("Tibero", "oracle");
//		properties.put("MySQL", "mysql");
//
//		VendorDatabaseIdProvider vendorDatabaseIdProvider = new VendorDatabaseIdProvider();
//		vendorDatabaseIdProvider.setProperties(properties);
//
//		return vendorDatabaseIdProvider;
//	}
//
//	@Bean
//	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws IOException {
//
//		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//		factoryBean.setDataSource(dataSource);
//		factoryBean.setDatabaseIdProvider(databaseIdProvider());
//		factoryBean.setConfigLocation(applicationContext.getResource("classpath:tigris/mybatis/mybatis-config.xml"));
//		factoryBean.setMapperLocations(applicationContext.getResources("classpath*:tigris/mybatis/sqls/*.xml"));
//		factoryBean.setVfs(SpringBootVFS.class);
//
//		return factoryBean;
//	}
//
//	@Bean
//    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//}

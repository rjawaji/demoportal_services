package com.pgba.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
/*import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
*/import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Component
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.pgba.db.dao", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "portalTransactionManager")
@PropertySource("classpath:database.properties")
public class DataSourceConfig {

	public static Logger logger = LogManager.getLogger(DataSourceConfig.class);

	@Autowired(required = false)
	private PersistenceUnitManager persistenceUnitManager;

	@Autowired
	Environment env;

	/*@Bean(name = "oracleDatasource")
	@Primary
		
	public DataSource jndiDataSource() {

		return new JndiDataSourceLookup().getDataSource("jdbc/portal");
		//return DataSourceBuilder.create().build();
	}*/
	
	 @Bean
	 public DataSource getDataSource() {
		 BasicDataSource dataSource = new BasicDataSource();
		 dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
		 dataSource.setUrl(env.getProperty("database.url"));
		 dataSource.setUsername(env.getProperty("database.username"));
		 dataSource.setPassword(env.getProperty("database.password"));
		 return dataSource;
	}

	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {

		LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
		lcemfb.setJpaVendorAdapter(jpaVendorAdapter());
		lcemfb.setDataSource(getDataSource());
		lcemfb.setPersistenceUnitName("myJpaPersistenceUnit");
		lcemfb.setPackagesToScan("com.pgba.db.model");
		lcemfb.setJpaProperties(jpaProperties());
		return lcemfb;
	}

	@Bean(name = "portalJpaVendorAdapter")
	JpaVendorAdapter jpaVendorAdapter() {
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.ORACLE);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(false);
		adapter.setDatabasePlatform("org.hibernate.dialect.Oracle10gDialect");
		return adapter;
	}

	@Bean(name = "portalTransactionManager")
	public PlatformTransactionManager portalTransactionManager(
			@Qualifier("entityManagerFactory") EntityManagerFactory oracleEntityManagerFactory) {
		JpaTransactionManager transManager = new JpaTransactionManager(oracleEntityManagerFactory);
		transManager.setDataSource(getDataSource());
		return transManager;

	}
	
	private Properties jpaProperties() {
		Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        return properties;        
    }	
}

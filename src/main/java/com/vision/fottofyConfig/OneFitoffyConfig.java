package com.vision.fottofyConfig;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "fitoffyEntityManagerFactory",
		basePackages = "com.vision.dbone.repository",
		transactionManagerRef = "fitoffyTransactionManager"
		
		)
public class OneFitoffyConfig
{
	
	@Autowired
	public Environment environment;


    @Primary
    @Bean(name="fitoffyDataSource")
    @ConfigurationProperties(prefix = "fitoffy.datasource")
    public DataSource dataSource()
	{
    	
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getProperty("fitoffy.datasource.url"));
		dataSource.setDriverClassName(environment.getProperty("fitoffy.datasource.driver-class-name"));
		dataSource.setUsername(environment.getProperty("fitoffy.datasource.username"));
		dataSource.setPassword(environment.getProperty("fitoffy.datasource.password"));
		
		return dataSource;
	}


    @Primary
    @Bean(name = "fitoffyEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,@Qualifier("fitoffyDataSource") DataSource dataSource)
	{
		Map<String, Object> properties = new HashMap<String, Object>() ;
	      properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	      properties.put("hibernate.show.sql", "true");
	      properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	      properties.put("spring.jpa.hibernate.naming.physical-strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");
	      properties.put("spring.jpa.hibernate.naming.implicit-strategy", "org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl");
	      properties.put("spring.jpa.properties.hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");
	      
	      return builder.dataSource(dataSource)
	              .properties(properties)
	              .packages("com.vision.dbone.entity")
	              .persistenceUnit("yogmania") // Database name
	              .build();
	      

		
		
		
	}


    @Primary
    @Bean(name ="fitoffyTransactionManager")
    PlatformTransactionManager transactionManager(@Qualifier("fitoffyEntityManagerFactory") EntityManagerFactory manager)
	{
		return new JpaTransactionManager(manager);
	}

	

}


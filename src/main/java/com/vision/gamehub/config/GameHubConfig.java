package com.vision.gamehub.config;

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
		entityManagerFactoryRef = "gamehubEntityManagerFactory",
		basePackages = {"com.vision.gamehub.repository"},
		transactionManagerRef = "gamehubTransactionManager"
		
		)
public class GameHubConfig 
{
	
		@Autowired
		private Environment environment;
		
		 
		@Bean(name="gamehubDataSource")
		@ConfigurationProperties(prefix = "gamehub.datasource")
		public DataSource dataSource()
		{

			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl(environment.getProperty("gamehub.datasource.url"));
			dataSource.setDriverClassName(environment.getProperty("gamehub.datasource.driver-class-name"));
			dataSource.setUsername(environment.getProperty("gamehub.datasource.username"));
			dataSource.setPassword(environment.getProperty("gamehub.datasource.password"));
			
			System.out.println("working... "+dataSource.getUrl() + " " + dataSource.getUsername());
			return dataSource;
		}
		
		
		

		
		@Bean("gamehubEntityManagerFactory")
		public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("gamehubDataSource") DataSource dataSource)
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
		              .packages("com.vision.gamehub.entity")
		              .persistenceUnit("h2n_Game_hub") // Database name
		              .build();
				

		}
		
		
		@Bean(name="gamehubTransactionManager")
		public PlatformTransactionManager transactionManager(@Qualifier("gamehubEntityManagerFactory") EntityManagerFactory manager)
		{
			return new JpaTransactionManager(manager);
		}

}


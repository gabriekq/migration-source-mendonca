package com.mendonca.mendoncamigrationsources.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

	
	@Primary
	@Bean(name="mySqlDataSource")
	@ConfigurationProperties(prefix = "mysq.datasource")
	public DataSource  dataSourceMySQL() {
		 return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	
	@Bean(name="postDataSource")
	@ConfigurationProperties(prefix = "post.datasource")
	public DataSource  dataSourcePost() {
		 return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}
	
	
	@Bean(name="JdbcTemplateMySql")
	@Autowired
	@Qualifier("mySqlDataSource")
	public  JdbcTemplate getJdbcTemplateMySQL() {
		return new JdbcTemplate(dataSourceMySQL());
	}
	
	
	
	
	@Bean(name="JdbcTemplatePostgreSql")
	@Autowired
	@Qualifier("postDataSource")
	public JdbcTemplate getJdbcTemplatePostgreSQL() {
		return new  JdbcTemplate(dataSourcePost());
	}
	
	
	
	
	
}

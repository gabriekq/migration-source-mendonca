package com.mendonca.mendoncamigrationsources.batch;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.mendonca.mendoncamigrationsources.model.Person;

@Component
public class DBWriter implements ItemWriter<Person>  {

	@Autowired
	@Qualifier("JdbcTemplatePostgreSql")
	private  JdbcTemplate jdbcTemplatePg;

	@Override
	public void write(List<? extends Person> items) throws Exception {
		
	   String sqlInsertPg = "INSERT INTO person (id, firstName, lastName, age , street, emaill ) VALUES ( :id_p ,:firstName_p ,:lastName_p ,:age_p ,:street_p, :emaill_p ); ";
		
	   NamedParameterJdbcTemplate  namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplatePg);
		
		
		for(Person item : items  ) {
			
		  //-- usar esse objeto aqui	
			SqlParameterSource params = new MapSqlParameterSource()
					.addValue("id_p", item.getId())
					.addValue("firstName_p", item.getFirstName() )
					.addValue("lastName_p", item.getLastName())
					.addValue("age_p", item.getAge())
					.addValue("street_p", item.getStreet() )
					.addValue("emaill_p", item.getEmaill() );  
		             
		namedJdbcTemplate.update(sqlInsertPg, params);
					
		//jdbcTemplatePg.update(sqlInsertPg,params);
		
		//jdbcTemplatePg.update(sqlInsertPg,item.getId() ,item.getFirstName() , item.getLastName()  ,item.getAge() ,item.getStreet() );
	
		}
		
		
		
		
		
	}
	
	
	
	
	
}

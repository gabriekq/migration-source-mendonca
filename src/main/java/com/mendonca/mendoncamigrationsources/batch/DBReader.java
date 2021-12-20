package com.mendonca.mendoncamigrationsources.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mendonca.mendoncamigrationsources.model.Person;

@Component
public class DBReader  implements ItemReader<Person> {

	@Autowired
	@Qualifier("JdbcTemplateMySql")
	private  JdbcTemplate jdbcTemplateMySql;
	
	
	private List<Person> peaple;
	
    private int index=-1;
	
	
	
	public void feachPeaple() {
		
		String mysqlSQL = "SELECT  id , age , first_name  , last_name  , street from person p   ;";
		   // Person person =     jdbcTemplateMySql.queryForObject(mysqlSQL,  Person.class);  
		    
		    
		    List<Person> peaple =     jdbcTemplateMySql.query(mysqlSQL,  new RowMapper<Person>(){  
	    	    
		        	   @Override  
		        	    public Person mapRow(ResultSet rs, int rownumber) throws SQLException {  
		        		     Person person= new Person();  
		        		     person.setId(rs.getInt("id"));  
		        	         person.setAge( rs.getInt("age") );
		        		     person.setFirstName(rs.getString("first_name"));
		        	         person.setLastName(rs.getString("last_name"));
		        	         person.setStreet(rs.getString("street"));
		        	        return person;  
		        	   }  
		        	   });  
		
		this.peaple = peaple;
		
	}
	
	
	
	
	
	// arrumar aqui e usar essa logica aqui o
	// https://stackoverflow.com/questions/43245742/spring-batch-read-step-running-in-loop
	
	@Override
	public  Person  read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		if(peaple == null) {
			feachPeaple();
		 
		}
	   
		index = index + 1;
		
		if(peaple.size() == index) {
			return null;
		}else {
		    return  peaple.get(index);	
		}
		
		
		
	}

}

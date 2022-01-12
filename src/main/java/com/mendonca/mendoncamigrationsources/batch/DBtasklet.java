package com.mendonca.mendoncamigrationsources.batch;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.mendonca.mendoncamigrationsources.model.Person;

@Component
public class DBtasklet implements Tasklet {

	
	@Autowired
	@Qualifier("JdbcTemplateMySql")
	private  JdbcTemplate jdbcTemplateMySql;
	
	
	@Autowired
	@Qualifier("JdbcTemplatePostgreSql")
	private  JdbcTemplate jdbcTemplatePostgreSql;
	
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("tasklet");
		
		String mySql = "SELECT COUNT(*) as  countMySql  FROM person p;";
		
		String PostgreSql = "SELECT COUNT(*) AS countPg FROM person p;";
		
		
	    List<Integer> countMySql  =	jdbcTemplateMySql.query(mySql, new RowMapper<Integer>(){  
    	    
     	   @Override  
     	    public Integer mapRow(ResultSet rs, int rownumber) throws SQLException {  
     		   int valor  = rs.getInt("countMySql");
     	        return valor;  
     	   }  
     	   });  
	    
	    
	    
	    List<Integer> countPostgreSql  =	jdbcTemplatePostgreSql.query(PostgreSql, new RowMapper<Integer>(){  
    	    
	     	   @Override  
	     	    public Integer mapRow(ResultSet rs, int rownumber) throws SQLException {  
	     		   int valor  = rs.getInt("countPg");
	     	        return valor;  
	     	   }  
	     	   });  
	    
	    
	    
	    System.err.println("row count in MySQL "+countMySql.get(0)+" row count PostGreSQL "+countPostgreSql.get(0));
	    
 
	   return  RepeatStatus.FINISHED;

	}

}

package com.mendonca.mendoncamigrationsources.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mendonca.mendoncamigrationsources.model.Person;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory,
			       StepBuilderFactory stepBuilderFactory,
			       ItemReader<Person> itemReader,
			       ItemWriter<Person> itemWriter
			      ){
		
		Step step = stepBuilderFactory.get("ETL-file-load")
				.<Person,Person>chunk(2)
		        .reader(itemReader)
		        .writer(itemWriter)
		        .build();
		
	    return	jobBuilderFactory.get("ETL-Load")
		        .incrementer(new RunIdIncrementer())
		        .start(step)
		        .build();
		}
	
	
	
	
}

package com.javainuse.config;

import java.io.File;
import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.javainuse.listener.JobCompletionListener;
import com.javainuse.processor.ExtractBeanProcessor;
import com.javainuse.rowmappers.ClaimsRowMapper;
import com.javainuse.util.ExtractPojo;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	/*@Autowired
	public DataSource datasource;*/

	/*@Bean
	public Job processJob() {
		return jobBuilderFactory.get("processJob")
				.incrementer(new RunIdIncrementer()).listener(listener())
				.flow(orderStep1()).end().build();
	}

	@Bean
	public Step orderStep1() {
		return stepBuilderFactory.get("orderStep1").<String, String> chunk(1)
				.reader(new Reader()).processor(new Processor())
				.writer(new Writer()).build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}
*/
	@Autowired
    public DataSource dataSource;
	
	
	 @Bean
	 public JdbcCursorItemReader<ExtractPojo> reader() {
	    	System.out.println("reading Item");
	    	JdbcCursorItemReader<ExtractPojo> reader = new JdbcCursorItemReader<ExtractPojo>();
	    	reader.setSql("select client_ID, batchSeq, createDate from ubbatch where batchSeq <=100 ");
	        reader.setDataSource(dataSource);
	        reader.setRowMapper(new ClaimsRowMapper());
	        reader.toString();
	        return reader;
	    }

	    @Bean
	    public ExtractBeanProcessor processor() throws Exception {
	    	System.out.println("processing Item");
	    	ExtractBeanProcessor extractBeanProcessor= new ExtractBeanProcessor();
	    	extractBeanProcessor.process(new ExtractPojo());
	        return extractBeanProcessor;
	    }

	    @Bean
	    public FlatFileItemWriter<ExtractPojo> writer() throws IOException {
	    	System.out.println("writing Item");
	    	FlatFileItemWriter<ExtractPojo> writer = new FlatFileItemWriter<ExtractPojo>();
	    	writer.setResource(new ClassPathResource("/extract/extract.txt"));
	    	DelimitedLineAggregator<ExtractPojo> delLineAgg = new DelimitedLineAggregator<ExtractPojo>();
	    	delLineAgg.setDelimiter(",");
	    	BeanWrapperFieldExtractor<ExtractPojo> fieldExtractor = new BeanWrapperFieldExtractor<ExtractPojo>();
	    	fieldExtractor.setNames(new String[] {"client_ID", "batchSeq", "Systdate"});
	    	delLineAgg.setFieldExtractor(fieldExtractor);
//	    	System.out.println(file.length());
	    	writer.setLineAggregator(delLineAgg);
	    	return writer;
	    }
	    
	    @Bean
	    public JobExecutionListener listener() {
	        return new JobCompletionListener();
	    } 
	    
	  /*
	    @Bean
	    public DataSource dataSource(){
	    	 DriverManagerDataSource dataSource = new DriverManagerDataSource();
	         dataSource.setDriverClassName(appProps.getDriverClassName());
	         dataSource.setUrl(appProps.getUrl());
	         dataSource.setUsername(appProps.getUserName());
	         dataSource.setPassword(appProps.getPassword());
	         return dataSource;     
	     } */

	    @Bean
	    public Job claimExtractJob(Step step1) {
	        return jobBuilderFactory.get("claimExtractJob")
	                .incrementer(new RunIdIncrementer()).listener(listener())
	                .flow(step1)
	                .end()
	                .build();
	    }

	    @Bean
	    public Step step1() throws Exception {
	        return stepBuilderFactory.get("step1")
	                .<ExtractPojo, ExtractPojo> chunk(10)
	                .reader(reader())
	                .processor(processor())
	                .writer(writer())
	                .build();
	    }
}

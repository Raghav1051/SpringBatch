package com.javainuse.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobCompletionListener extends JobExecutionListenerSupport {

	

	/*private final JdbcTemplate jdbcTemplate;



	@Autowired

	public JobCompletionListener(JdbcTemplate jdbcTemplate) {

		this.jdbcTemplate = jdbcTemplate;

	}*/
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("BATCH JOB COMPLETED SUCCESSFULLY");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("****************************** BATCH JOB WILL START *********************************");
    }



}

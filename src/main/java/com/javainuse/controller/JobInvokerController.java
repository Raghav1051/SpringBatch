package com.javainuse.controller;
 
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.config.BatchConfig;

 
/*@RestController
public class JobInvokerController {
 
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    Job processJob;
 
    @RequestMapping("/invokejob")
    public String handle() throws Exception {
 
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processJob, jobParameters);
 
        return "Batch job has been invoked";
    }
//}
*/

@EnableBatchProcessing
@RestController
@ComponentScan
public class JobInvokerController {
	
	

	
	@Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping("/launchjob")
    public String handle() throws Exception {

        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Done";
    }
	
}
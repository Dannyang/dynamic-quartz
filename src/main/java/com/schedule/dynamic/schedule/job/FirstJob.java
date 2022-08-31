package com.schedule.dynamic.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class FirstJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("First job started at " + System.currentTimeMillis());
    }
}

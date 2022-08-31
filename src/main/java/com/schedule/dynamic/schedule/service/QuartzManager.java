package com.schedule.dynamic.schedule.service;

import com.schedule.dynamic.schedule.entity.SysTask;
import com.schedule.dynamic.schedule.job.FirstJob;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
public class QuartzManager {
    private final Scheduler scheduler;

    public QuartzManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void addJob(SysTask task) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(FirstJob.class).withIdentity(task.getJobName(), task.getJobGroup()).build();
        // 定义调度触发规则
        // 使用cornTrigger规则
        Trigger trigger =
                TriggerBuilder.newTrigger().startNow().withIdentity(task.getJobName(), task.getJobGroup()).withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression())).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停执行的任务
     *
     * @param task
     * @throws SchedulerException
     */
    public void pauseJob(SysTask task) throws SchedulerException {
        JobKey jobKey = new JobKey(task.getJobName(), task.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     *
     * @param task
     * @throws SchedulerException
     */
    public void resumeJob(SysTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除执行的任务
     *
     * @param task
     * @throws SchedulerException
     */
    public void deleteJob(SysTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即执行某个任务
     *
     * @param task
     * @throws SchedulerException
     */
    public void runJobNow(SysTask task) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    public void updateJobCron(SysTask task) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());
        // 根据任务名称拿到触发器
        CronTrigger theTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 更新触发器定时cron配置
        Trigger newTrigger =
                theTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression())).build();
        // 重新执行任务
        scheduler.rescheduleJob(triggerKey, newTrigger);
    }
}

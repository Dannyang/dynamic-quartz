package com.schedule.dynamic.schedule.runner;

import com.schedule.dynamic.schedule.constant.JobStatusEnum;
import com.schedule.dynamic.schedule.dao.TaskMapper;
import com.schedule.dynamic.schedule.entity.SysTask;
import com.schedule.dynamic.schedule.service.QuartzManager;
import org.quartz.SchedulerException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitTaskRunner implements ApplicationRunner {
    private final TaskMapper taskMapper;
    private final QuartzManager quartzManager;

    public InitTaskRunner(TaskMapper taskMapper, QuartzManager quartzManager) {
        this.taskMapper = taskMapper;
        this.quartzManager = quartzManager;
    }
    //springboot启动时自动装载定时任务
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initSchedule();
    }

    public void initSchedule() throws SchedulerException {
        // 这里获取任务信息数据
        List<SysTask> jobList = taskMapper.getTasks();
        for (SysTask task : jobList) {
            if (JobStatusEnum.RUNNING.getCode().equals(task.getJobStatus())) {
                quartzManager.addJob(task);
            }
        }
    }
}

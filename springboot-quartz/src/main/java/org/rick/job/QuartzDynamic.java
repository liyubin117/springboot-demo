package org.rick.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class QuartzDynamic {

    @Autowired
    private Scheduler scheduler;

    /**
     * 创建定时任务
     *
     * @param jobName
     * @param jobGroup
     * @param cron
     * @param triggerName
     * @param triggerGroup
     * @throws SchedulerException
     */
    //http://localhost:7022/create?jobName=task1&jobGroup=group1&cron=*/10 * * * * ?&triggerName=trigger1&triggerGroup=tGroup1
    @GetMapping("create")
    public String createJob(String jobName, String jobGroup, String cron, String triggerName, String triggerGroup) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        // 如果存在这个任务，则删除
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }

        JobDetail jobDetail = JobBuilder.newJob(PrintJob.class)
                .withIdentity(jobKey)
                .usingJobData("count", 0)
                .build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron).withMisfireHandlingInstructionFireAndProceed();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroup)
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        return "job created!";
    }

    /**
     * 暂停定时任务
     *
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    @GetMapping("pause")
    public void pauseJob(String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复定时任务
     *
     * @param jobName
     * @param jobGroup
     */
    @GetMapping("resume")
    public void resumeJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除定时任务
     *
     * @param jobName
     * @param jobGroup
     * @param triggerName
     * @param triggerGroup
     * @throws SchedulerException
     */
    @GetMapping("remove")
    public String removeJob(String jobName, String jobGroup, String triggerName, String triggerGroup) throws SchedulerException {

        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (trigger == null) {
            return "trigger not exists!";
        }
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
        return "job removed!";
    }
}

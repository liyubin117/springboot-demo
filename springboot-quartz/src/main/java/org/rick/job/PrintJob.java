package org.rick.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yubin Li
 * @date 2022/1/17 16:40
 **/
@DisallowConcurrentExecution  //禁止并发执行(Quartz不要并发地执行同一个job定义（这里指一个job类的多个实例）)
@PersistJobDataAfterExecution
@Slf4j
public class PrintJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Integer count = (Integer)jobExecutionContext.getJobDetail().getJobDataMap().get("count");
            count++;
            if (count > 5) {
                throw new Exception();
            }
            jobExecutionContext.getJobDetail().getJobDataMap().put("count", count);
            System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                    + "---" + "scheduled dynamically!" + " " + count);
        } catch (Exception e) {
            log.error("cron job failed");
            JobExecutionException e2 = new JobExecutionException(e);
            e2.setUnscheduleAllTriggers(true);  //遇到执行异常后关掉此任务的所有trigger
            throw e2;
        }
    }
}

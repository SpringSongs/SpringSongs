package io.github.springsongs.modules.job.domain;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.springsongs.modules.job.dto.SpringJobHistoryDTO;
import io.github.springsongs.modules.job.service.ISpringJobHistoryService;

@Component
public abstract class AbstractBaseJob implements SpringBaseJob {

	@Autowired
	private ISpringJobHistoryService springJobHistoryService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	SpringJobHistoryDTO history = new SpringJobHistoryDTO();
        history.setGroupCode(context.getTrigger().getJobKey().getGroup());
        history.setSimpleClassName(context.getTrigger().getJobKey().getName());
        history.setCreatedOn(new Date());
        doExecute(context, history);
        springJobHistoryService.insert(history);
    }

    public abstract void doExecute(JobExecutionContext context, SpringJobHistory history);

}

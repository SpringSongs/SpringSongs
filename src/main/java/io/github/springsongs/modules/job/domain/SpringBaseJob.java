package io.github.springsongs.modules.job.domain;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface SpringBaseJob extends Job{
	void execute(JobExecutionContext context) throws JobExecutionException;
}

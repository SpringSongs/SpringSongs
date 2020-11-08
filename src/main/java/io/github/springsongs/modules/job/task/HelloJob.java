package io.github.springsongs.modules.job.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.springsongs.modules.job.domain.SpringBaseJob;
import io.github.springsongs.modules.job.service.impl.SpringJobServiceImpl;

public class HelloJob implements SpringBaseJob {
	static Logger logger = LoggerFactory.getLogger(SpringJobServiceImpl.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String result = "Hello Job执行时间: " + new Date();
		logger.info(result);
	}

}

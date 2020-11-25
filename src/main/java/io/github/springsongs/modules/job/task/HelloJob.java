package io.github.springsongs.modules.job.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.springsongs.modules.job.domain.AbstractBaseJob;
import io.github.springsongs.modules.job.domain.SpringJobHistory;
import io.github.springsongs.modules.job.service.impl.SpringJobServiceImpl;

public class HelloJob extends AbstractBaseJob {
	static Logger logger = LoggerFactory.getLogger(SpringJobServiceImpl.class);

	@Override
	public void doExecute(JobExecutionContext context, SpringJobHistory history) {
		String result = "Hello Job执行时间: " + new Date();
		logger.info(result);
	}
}

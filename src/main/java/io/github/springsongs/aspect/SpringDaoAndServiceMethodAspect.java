package io.github.springsongs.aspect;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;

@Configuration
public class SpringDaoAndServiceMethodAspect {

    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();
        return dsInterceptor;
    }

    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("io.github.springsongs.modules.activiti.repo.*");
        pointcut.setPattern("io.github.springsongs.modules.job.repo.*");
        pointcut.setPattern("io.github.springsongs.modules.sys.repo.*");
        pointcut.setPattern("io.github.springsongs.modules.activiti.service.*");
        pointcut.setPattern("io.github.springsongs.modules.job.service.*");
        pointcut.setPattern("io.github.springsongs.modules.sys.service.*");
        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }
}

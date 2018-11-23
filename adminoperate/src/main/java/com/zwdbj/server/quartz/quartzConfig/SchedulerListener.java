package com.zwdbj.server.quartz.quartzConfig;

import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdScheduler;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent>
, ServletContextListener
{
    @Autowired
    public MyScheduler myScheduler;
    @Autowired
    SpringJobFactory springJobFactory;
    @Autowired
    DataSource dataSource;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            try {
                myScheduler.scheduleJobs();
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
    }

    @Bean(value = "mySchedulerFactoryBean2",autowire = Autowire.BY_NAME)
    public SchedulerFactoryBean mySchedulerFactoryBean()throws IOException, PropertyAccessException {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setOverwriteExistingJobs(true);
        bean.setStartupDelay(1);
        bean.setJobFactory(springJobFactory);
        bean.setQuartzProperties(properties());
        bean.setWaitForJobsToCompleteOnShutdown(true);
        bean.setAutoStartup(true);
        bean.setDataSource(dataSource);
        return bean;
    }

        @Bean
        public Properties properties() throws IOException {
            Properties prop = new Properties();
            prop.load(new ClassPathResource("quartz.properties").getInputStream());
            return prop;
        }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        WebApplicationContext webApplicationContext = (WebApplicationContext) servletContextEvent
                .getServletContext()
                .getAttribute(
                        WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        StdScheduler startQuartz = (StdScheduler) webApplicationContext
                .getBean("mySchedulerFactoryBean2", SchedulerFactory.class);
        if(startQuartz != null) {
            startQuartz.shutdown();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }



}

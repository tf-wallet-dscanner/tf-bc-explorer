package io.dkargo.bcexplorer.scheduler;

import com.munzi.munzischeduler.SchedulerWorkerManager;
import io.dkargo.bcexplorer.scheduler.config.SchedulerInstance;
import log.munzi.interceptor.config.ApiLogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "io.dkargo")
@EnableConfigurationProperties(ApiLogProperties.class)
public class DkargoBcExplorerSchedulerApplication {

    @Autowired
    private SchedulerInstance schedulerInstance;

    public void start(ApplicationContext context) {

        SchedulerWorkerManager schedulerWorkerManager = new SchedulerWorkerManager(schedulerInstance);
        schedulerWorkerManager.startWorker(context);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DkargoBcExplorerSchedulerApplication.class, args);
        ((DkargoBcExplorerSchedulerApplication) context.getBean("dkargoBcExplorerSchedulerApplication")).start(context);
    }
}

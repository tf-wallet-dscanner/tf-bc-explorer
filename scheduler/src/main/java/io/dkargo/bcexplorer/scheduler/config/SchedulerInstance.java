package io.dkargo.bcexplorer.scheduler.config;

import com.munzi.munzischeduler.config.ASchedulerInstance;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "scheduler")
public class SchedulerInstance extends ASchedulerInstance {
}

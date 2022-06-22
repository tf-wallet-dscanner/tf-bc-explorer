package io.dkargo.bcexplorer.collector;

import log.munzi.interceptor.config.ApiLogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAspectJAutoProxy
@EnableMongoRepositories(basePackages = "io.dkargo")
@SpringBootApplication(scanBasePackages = "io.dkargo")
@EnableConfigurationProperties(ApiLogProperties.class)
public class DkargoBcExplorerCollectorApplication {

    public static void main(String[] args) { SpringApplication.run(DkargoBcExplorerCollectorApplication.class, args); }
}

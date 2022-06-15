package io.dkargo.bcexplorer.api;

import log.munzi.interceptor.config.ApiLogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = "io.dkargo")
@EnableConfigurationProperties(ApiLogProperties.class)
public class DkargoBcExplorerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DkargoBcExplorerApiApplication.class, args);
    }
}

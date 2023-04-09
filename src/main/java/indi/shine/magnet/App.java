package indi.shine.magnet;

import indi.shine.magnet.config.ScheduleTask;
import indi.shine.web.util.BeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.mongo.MongoMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

@EnableScheduling
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoMetricsAutoConfiguration.class})
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        CookieManager manager = new CookieManager();
        manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(manager);
        BeanUtil.getBean(ScheduleTask.class).refreshCookie();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
}
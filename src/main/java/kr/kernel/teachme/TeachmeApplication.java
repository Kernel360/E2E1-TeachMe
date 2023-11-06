package kr.kernel.teachme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class TeachmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachmeApplication.class, args);
    }
}

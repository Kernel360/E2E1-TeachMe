package kr.kernel.teachme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TeachmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachmeApplication.class, args);
    }
}

package kr.kernel360.teachme;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication

public class TeachmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachmeApplication.class, args);
    }

}

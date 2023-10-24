package kr.kernel360.teachme;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import kr.kernel360.teachme.lecture.service.CrawlingFastcampus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeachmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachmeApplication.class, args);
    }

}

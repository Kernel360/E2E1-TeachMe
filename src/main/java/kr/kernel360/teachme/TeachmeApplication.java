package kr.kernel360.teachme;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import kr.kernel360.teachme.lecture.service.CrawlingFastcampus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeachmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeachmeApplication.class, args);
        CrawlingFastcampus crawler = new CrawlingFastcampus("https://fastcampus.co.kr/.api/www/categories/1/page");
        FastcampusResponse response = crawler.getFastcampusResponse();
        System.out.println(response.getData().toString());
    }

}

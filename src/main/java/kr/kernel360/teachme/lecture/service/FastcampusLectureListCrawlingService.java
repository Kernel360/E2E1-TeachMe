package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import org.springframework.web.client.RestTemplate;

public class FastcampusLectureListCrawlingService {
    private String apiUrl;

    public FastcampusLectureListCrawlingService(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public FastcampusResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        FastcampusResponse fastcampusResponse = restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/1/page", FastcampusResponse.class);



        return fastcampusResponse;
    }

    public static void main(String[] args) {
        FastcampusLectureListCrawlingService crawl = new FastcampusLectureListCrawlingService("https://fastcampus.co.kr/.api/www/categories/1/page");
        System.out.println(crawl.getFastcampusResponse().getData().getCategoryInfo().getCourses().get(0).toString());
    }
}

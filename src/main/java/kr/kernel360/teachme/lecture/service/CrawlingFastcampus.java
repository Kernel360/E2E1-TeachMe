package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import org.springframework.web.client.RestTemplate;

public class CrawlingFastcampus {
    private String apiUrl;

    public CrawlingFastcampus(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public FastcampusResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        FastcampusResponse fastcampusResponse = restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/1/page", FastcampusResponse.class);

        return fastcampusResponse;
    }

    public static void main(String[] args) {
        CrawlingFastcampus crawl = new CrawlingFastcampus("https://fastcampus.co.kr/.api/www/categories/1/page");
        System.out.println(crawl.getFastcampusResponse().getData().getCategoryInfo().getCourses().get(0).toString());
    }
}

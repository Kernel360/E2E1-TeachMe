package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import org.springframework.web.client.RestTemplate;

public class CrawlingFastcampus {
    private String apiUrl;

    public CrawlingFastcampus(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public FastcampusResponse getFastcampusResponse() {
        System.out.println(apiUrl);

        RestTemplate restTemplate = new RestTemplate();

        FastcampusResponse fastcampusResponse = restTemplate.getForObject(apiUrl, FastcampusResponse.class);


        return fastcampusResponse;
    }
}

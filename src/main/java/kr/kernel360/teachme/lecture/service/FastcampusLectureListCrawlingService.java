package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureListResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class FastcampusLectureListCrawlingService {


    public static FastcampustLectureListResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        FastcampustLectureListResponse fastcampusResponse = restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/all", FastcampustLectureListResponse.class);

        return fastcampusResponse;
    }

    public static void main(String[] args) {
        FastcampustLectureListResponse crawledData = getFastcampusResponse();
        System.out.println(crawledData.getData().getCategoryList().get(1).getCourses().get(0).getDesktopCardAsset());
    }
}

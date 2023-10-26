package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureListResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FastcampusLectureListCrawlingService {

    public static FastcampustLectureListResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        FastcampustLectureListResponse fastcampusResponse = restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/all", FastcampustLectureListResponse.class);

        return fastcampusResponse;
    }
    public static List<FastcampustLectureResponse> convertLectureListToLecture(FastcampustLectureListResponse lectureList) {
        ModelMapper modelMapper = new ModelMapper();

        return lectureList.getData().getCategoryList().stream()
                .flatMap(category -> category.getCourses().stream())
                .map(course -> modelMapper.map(course, FastcampustLectureResponse.class))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        FastcampustLectureListResponse crawledData = getFastcampusResponse();
        List<FastcampustLectureResponse> lectures = convertLectureListToLecture(crawledData);
        System.out.println(lectures.get(0).toString());
    }

    public void crawling() {

    }
}

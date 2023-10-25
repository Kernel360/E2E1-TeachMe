package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureListResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class FastcampusLectureListCrawlingService {

    public static FastcampustLectureListResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        FastcampustLectureListResponse fastcampusResponse = restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/all", FastcampustLectureListResponse.class);

        return fastcampusResponse;
    }
    public static List<FastcampustLectureResponse> convertLectureListToLecture(FastcampustLectureListResponse lecutreList) {
        List<FastcampustLectureResponse> lectures = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for(var i = 0; i < lecutreList.getData().getCategoryList().size(); i++) {
            for(var j = 0; j < lecutreList.getData().getCategoryList().get(i).getCourses().size(); j++) {
                lectures.add(modelMapper.map(lecutreList.getData().getCategoryList().get(i).getCourses().get(j), FastcampustLectureResponse.class));
            }
        }
        return lectures;
    }

    public static void main(String[] args) {
        FastcampustLectureListResponse crawledData = getFastcampusResponse();
        List<FastcampustLectureResponse> lectures = convertLectureListToLecture(crawledData);
        System.out.println(lectures.get(0).toString());
    }
}

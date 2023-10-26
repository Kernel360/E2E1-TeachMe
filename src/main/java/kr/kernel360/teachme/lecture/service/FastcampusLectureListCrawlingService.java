package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureListResponse;
import kr.kernel360.teachme.lecture.dto.FastcampustLectureResponse;
import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import kr.kernel360.teachme.lecture.repository.FastcampusRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class FastcampusLectureListCrawlingService {

    private final FastcampusRepository fastcampusRepository;
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

    public List<FastcampustLectureResponse> create() {
        FastcampustLectureListResponse crawledData = getFastcampusResponse();
        List<FastcampustLectureResponse> lectures = convertLectureListToLecture(crawledData);
        List<FastcampusLecture> lectureList = new ArrayList<>();
        for (FastcampustLectureResponse lecture : lectures){
            lectureList.add(lecture.toEntity());
        }
        fastcampusRepository.saveAll(lectureList);
        return lectures;
    }
}

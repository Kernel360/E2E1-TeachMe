package kr.kernel.teachme.domain.crawler.service;

import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureListResponse;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class FastcampusLectureListCrawlingService {

    private final LectureRepository lectureRepository;

    private static final String platform = "fastcampus";

    public static FastcampusLectureListResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/all", FastcampusLectureListResponse.class);
    }
    public static List<FastcampusLectureResponse> convertLectureListToLecture(FastcampusLectureListResponse lectureList) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<FastcampusLectureListResponse.Course, FastcampusLectureResponse>() {
            @Override
            protected void configure() {
                using(ctx -> ((FastcampusLectureListResponse.Course) ctx.getSource()).getFormat().getCreatedAt())
                        .map(source, destination.getCreatedAt());
                using(ctx -> ((FastcampusLectureListResponse.Course) ctx.getSource()).getFormat().getUpdatedAt())
                        .map(source, destination.getUpdatedAt());
            }
        });

        return lectureList.getData().getCategoryList().stream()
                .flatMap(category -> category.getCourses().stream())
                .map(course -> modelMapper.map(course, FastcampusLectureResponse.class))
                .collect(Collectors.toList());
    }

    public boolean isAtLeastOneRowExists() {
        return lectureRepository.countByPlatform(platform) > 0;
    }

    public void create() {
        if(isAtLeastOneRowExists()) throw new CrawlerException("크롤링 불가 상태");
        FastcampusLectureListResponse crawledData = getFastcampusResponse();
        List<FastcampusLectureResponse> lectures = new ArrayList<>();
        try {
            lectures = convertLectureListToLecture(crawledData);
        } catch (NullPointerException e) {
            throw new CrawlerException("크롤링 중 오류 발생 : 크롤링 된 데이터가 없습니다.", e);
        }

        List<Lecture> fastcampuslectureList = new ArrayList<>();
        for (FastcampusLectureResponse lecture : lectures){
            fastcampuslectureList.add(lecture.toEntity());
        }
        lectureRepository.saveAll(fastcampuslectureList);
    }

}

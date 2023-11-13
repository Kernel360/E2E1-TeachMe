package kr.kernel.teachme.domain.crawler.service;

import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureListResponse;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class FastcampusLectureListCrawlingService {

    private final LectureRepository lectureRepository;
    private static final String PLATFORM = "fastcampus";

    @Value("${url.fastcampus.list}")
    private static String BASE_URL;

    private FastcampusLectureListResponse fetchFastcampusData() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(BASE_URL, FastcampusLectureListResponse.class);
    }

    private List<FastcampusLectureResponse> mapCoursesToLectures(FastcampusLectureListResponse lectureList) {
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

    private void checkRowExistence() {
        if (lectureRepository.countByPlatform(PLATFORM) > 0) {
            throw new CrawlerException("크롤링 불가 상태");
        }
    }

    private void saveFastcampusLectures() {
        List<FastcampusLectureResponse> lectures = fetchAndConvertLectures();
        List<Lecture> fastcampusLectureList = lectures.stream()
                .map(FastcampusLectureResponse::toEntity)
                .collect(Collectors.toList());
        lectureRepository.saveAll(fastcampusLectureList);
    }

    private List<FastcampusLectureResponse> fetchAndConvertLectures() {
        FastcampusLectureListResponse crawledData = fetchFastcampusData();
        List<FastcampusLectureResponse> lectures;
        try {
            lectures = mapCoursesToLectures(crawledData);
        } catch (NullPointerException e) {
            throw new CrawlerException("크롤링 중 오류 발생 : 크롤링 된 데이터가 없습니다.", e);
        }
        return lectures;
    }

    public void create() {
        checkRowExistence();
        saveFastcampusLectures();
    }
}

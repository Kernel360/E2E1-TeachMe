package kr.kernel.teachme.lecture.service;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.lecture.dto.FastcampusLectureListResponse;
import kr.kernel.teachme.lecture.dto.FastcampusLectureResponse;
import kr.kernel.teachme.lecture.entity.FastcampusLecture;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.repository.FastcampusRepository;
import kr.kernel.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class FastcampusLectureListCrawlingService {

    private final FastcampusRepository fastcampusRepository;
    private final LectureRepository lectureRepository;
    public static FastcampusLectureListResponse getFastcampusResponse() {

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject("https://fastcampus.co.kr/.api/www/categories/all", FastcampusLectureListResponse.class);
    }
    public static List<FastcampusLectureResponse> convertLectureListToLecture(FastcampusLectureListResponse lectureList) {
        ModelMapper modelMapper = new ModelMapper();

        return lectureList.getData().getCategoryList().stream()
                .flatMap(category -> category.getCourses().stream())
                .map(course -> modelMapper.map(course, FastcampusLectureResponse.class))
                .collect(Collectors.toList());
    }

    public boolean isAtLeastOneRowExists() {
        return fastcampusRepository.count() > 0;
    }
    public List<Lecture> toLectureList(List<FastcampusLectureResponse> lectures) {
        List<Lecture> lectureList = new ArrayList<>();
        for (FastcampusLectureResponse lecture : lectures){
            lectureList.add(lecture.toLectureEntity());
        }
        return lectureList;
    }
    @Transactional
    public void create() {
        if(isAtLeastOneRowExists()) throw new CrawlerException("크롤링 불가 상태");
        FastcampusLectureListResponse crawledData = getFastcampusResponse();
        List<FastcampusLectureResponse> lectures = new ArrayList<>();
        try {
            lectures = convertLectureListToLecture(crawledData);
        } catch (NullPointerException e) {
            throw new CrawlerException("크롤링 중 오류 발생 : 크롤링 된 데이터가 없습니다.", e);
        }

        List<FastcampusLecture> fastcampuslectureList = new ArrayList<>();
        for (FastcampusLectureResponse lecture : lectures){
            fastcampuslectureList.add(lecture.toEntity());
        }
        lectureRepository.saveAll(toLectureList(lectures));
        fastcampusRepository.saveAll(fastcampuslectureList);
    }

}

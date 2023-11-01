package kr.kernel.teachme.crawler.service;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.crawler.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class FastcampusLectureDetailCrawlingService {
    private final LectureRepository lectureRepository;

    private static final String platform = "fastcampus";

    private List<Lecture> getFastcampusDetailResponse(List<Lecture> fastcampusLectures) throws InterruptedException {
        List<Lecture> fastcampusLectureList = new ArrayList<>();
        List<Lecture> deleteLectureList = new ArrayList<>();
        String baseUrl = "https://fastcampus.co.kr/.api/www/courses/";

        for (Lecture lecture : fastcampusLectures){
            String detailUrl = baseUrl + lecture.getLectureId() + "/products";
            FastcampusLectureDetailResponse response = getDetailResponse(detailUrl);

            List<FastcampusLectureDetailResponse.Products> productsList = response.getData().getProducts();

            if(productsList.isEmpty()) {
                deleteLectureList.add(lecture);
                continue;
            }

            FastcampusLectureDetailResponse.Products products = productsList.get(0);

            int listPrice = products.getListPrice();
            int salePrice = products.getSalePrice();
            String instructor = response.getData().getCourse().getInstructor();
            int totalClassHours = response.getData().getCourse().getTotalClassHours();

            lecture.updateFastcampusDetailInfo(listPrice, salePrice, instructor, totalClassHours * 60);
            fastcampusLectureList.add(lecture);

            Thread.sleep(500);
        }
        deleteNoLectureList(deleteLectureList);
        return fastcampusLectureList;
    }

    private void deleteNoLectureList(List<Lecture> lectureList) {
        lectureRepository.deleteAll(lectureList);
    }

    private FastcampusLectureDetailResponse getDetailResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url,FastcampusLectureDetailResponse.class);
    }


    public void update(){
        if (!lectureRepository.existsByDetailUploadFlagIsFalseAndPlatform(platform)) throw new CrawlerException("업데이트 할 데이터가 없습니다.");
        List<Lecture> fastcampusLectures = lectureRepository.findAllByDetailUploadFlagIsFalseAndPlatform(platform);
        try {
            List<Lecture> fastcampusLectureList = getFastcampusDetailResponse(fastcampusLectures);
            lectureRepository.saveAll(fastcampusLectureList);
        }catch (InterruptedException e){
            throw new CrawlerException("크롤링 중 에러 발생", e);
        }
    }

}
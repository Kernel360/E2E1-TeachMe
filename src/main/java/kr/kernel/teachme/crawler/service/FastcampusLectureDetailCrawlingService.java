package kr.kernel.teachme.crawler.service;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.crawler.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.crawler.entity.FastcampusLecture;
import kr.kernel.teachme.crawler.repository.FastcampusRepository;
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
    private final FastcampusRepository fastcampusRepository;
    private final LectureRepository lectureRepository;
    private List<FastcampusLecture> getFastcampusDetailResponse(List<FastcampusLecture> fastcampusLectures) throws InterruptedException {
        List<FastcampusLecture> fastcampusLectureList = new ArrayList<>();
        String baseUrl = "https://fastcampus.co.kr/.api/www/courses/";

        for (FastcampusLecture lecture : fastcampusLectures){
            String detailUrl = baseUrl + lecture.getUniqueId() + "/products";
            FastcampusLectureDetailResponse response = getDetailResponse(detailUrl);

            List<FastcampusLectureDetailResponse.Products> productsList = response.getData().getProducts();

            if(productsList.isEmpty()) {
                deleteLecture(lecture);
                continue;
            }

            FastcampusLectureDetailResponse.Products products = productsList.get(0);

            int categoryId = products.getCategoryId();
            int subCategoryId = products.getSubCategoryId();
            int listPrice = products.getListPrice();
            int salePrice = products.getSalePrice();
            String instructor = response.getData().getCourse().getInstructor();
            int totalClassHours = response.getData().getCourse().getTotalClassHours();

            lecture.updateDetailInfo(categoryId,subCategoryId,listPrice,salePrice,instructor,totalClassHours);
            fastcampusLectureList.add(lecture);

            Thread.sleep(500);
        }
        return fastcampusLectureList;
    }

    private List<FastcampusLecture> getLectureIdsWithFalseDetailUploadFlag() {
        //false만 가져오기
        return fastcampusRepository.findAllByDetailUploadFlagFalse();
    }

    private FastcampusLectureDetailResponse getDetailResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url,FastcampusLectureDetailResponse.class);
    }

    private void deleteLecture (FastcampusLecture lecture) {
        lectureRepository.deleteByLectureId(lecture.getUniqueId());
        fastcampusRepository.delete(lecture);
    }

    public void update(){
        if (!fastcampusRepository.existsByDetailUploadFlagIsFalse()) throw new CrawlerException("업데이트 할 데이터가 없습니다.");
        List<FastcampusLecture> fastcampusLectures = getLectureIdsWithFalseDetailUploadFlag();
        try {
            List<FastcampusLecture> fastcampusLectureList = getFastcampusDetailResponse(fastcampusLectures);
            fastcampusRepository.saveAll(fastcampusLectureList);
        }catch (Exception e){
            throw new CrawlerException("크롤링 중 에러 발생", e);
        }
    }

}
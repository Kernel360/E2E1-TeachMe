package kr.kernel.teachme.lecture.service;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.lecture.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.lecture.entity.FastcampusLecture;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.repository.FastcampusRepository;
import kr.kernel.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class FastcampusLectureDetailCrawlingService {
    private final FastcampusRepository fastcampusRepository;
    private final LectureRepository lectureRepository;

    public void deleteLecture (FastcampusLecture lecture) {
        lectureRepository.deleteByLectureId(lecture.getUniqueId());
        fastcampusRepository.delete(lecture);
    }
    public List<FastcampusLecture> getFastcampusDetailResponse(List<FastcampusLecture> fastcampusLectures){
        List<FastcampusLecture> fastcampusLectureList = new ArrayList<>();
        String baseUrl = "https://fastcampus.co.kr/.api/www/courses/";
        RestTemplate restTemplate = new RestTemplate();

        for (FastcampusLecture lecture : fastcampusLectures){
            try{
                String detailUrl = baseUrl + lecture.getUniqueId() + "/products";
                FastcampusLectureDetailResponse response = restTemplate.getForObject(detailUrl,FastcampusLectureDetailResponse.class);
                List<FastcampusLectureDetailResponse.Products> productsList = response.getData().getProducts();
                if (productsList.isEmpty()) {
                    deleteLecture(lecture);
                    continue;
                }
                FastcampusLectureDetailResponse.Products products = response.getData().getProducts().get(0);

                int categoryId = products.getCategoryId();
                int subCategoryId = products.getSubCategoryId();
                int listPrice=products.getListPrice();
                int salePrice=products.getSalePrice();
                String instructor=response.getData().getCourse().getInstructor();
                int totalClassHours= response.getData().getCourse().getTotalClassHours();

                lecture.updateDetailInfo(categoryId,subCategoryId,listPrice,salePrice,instructor,totalClassHours);
                fastcampusLectureList.add(lecture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fastcampusLectureList;
    }

    public List<FastcampusLecture> getLectureIdsWithFalseDetailUploadFlag() {
        //false만 가져오기
        return fastcampusRepository.findAllByDetailUploadFlagFalse();
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
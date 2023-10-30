package kr.kernel360.teachme.lecture.service;

import kr.kernel360.teachme.lecture.dto.FastcampusLectureDetailResponse;
import kr.kernel360.teachme.lecture.entity.FastcampusLecture;
import kr.kernel360.teachme.lecture.entity.Lecture;
import kr.kernel360.teachme.lecture.repository.FastcampusRepository;
import kr.kernel360.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FastcampusLectureDetailCrawlingService {
    private FastcampusRepository fastcampusRepository;
    private LectureRepository lectureRepository;

    public List<FastcampusLectureDetailResponse> getFastcampusDetailResponse(List<Long> fastcampusLectureUniqueIds){
        List<FastcampusLectureDetailResponse> responses = new ArrayList<>();
        String baseUrl = "https://fastcampus.co.kr/.api/www/courses/";
        RestTemplate restTemplate = new RestTemplate();
        for (Long fastcampusLectureUniqueId : fastcampusLectureUniqueIds){
            String detailUrl = baseUrl + fastcampusLectureUniqueId + "/products";
            FastcampusLectureDetailResponse response = restTemplate.getForObject(detailUrl,FastcampusLectureDetailResponse.class);
            responses.add(response);
        }
        return responses;
    }

    public List<Long> getLectureIdsWithFalseDetailUploadFlag() {
        //false만 가져오기
        List<FastcampusLecture> lecturesWithFalseFlag = fastcampusRepository.findByDetailUploadFlagFalse();
        List<Long> fastcampusLectureUniqueIds = lecturesWithFalseFlag.stream()
                .map(FastcampusLecture::getUniqueId)
                .collect(Collectors.toList());
        return fastcampusLectureUniqueIds;
    }

    // responses값 가져와서 패캠 렉처(엔티티)에 다시 넣기 -> saveall()
    public void setFastcampusDetail(List<FastcampusLectureDetailResponse> responses){
        int categoryId;
        int subCategoryId;
        int listPrice;
        int salePrice;
        String instructor;
        String totalClassHours;
        //

        for (FastcampusLectureDetailResponse response:responses){
            categoryId = response.getData().getProducts().getCategoryId();
            subCategoryId = response.getData().getProducts().getSubCategoryId();
            listPrice=response.getData().getProducts().getListPrice();
            salePrice=response.getData().getProducts().getSalePrice();
            instructor=response.getData().getCourse().getInstructor();
            totalClassHours=response.getData().getCourse().getTotalClassHours();



        }



    }

    public void update(){
        List<Long> fastcampusLectureUniqueIds = getLectureIdsWithFalseDetailUploadFlag();
        List<FastcampusLectureDetailResponse> detailResponses = getFastcampusDetailResponse(fastcampusLectureUniqueIds);


    }




}

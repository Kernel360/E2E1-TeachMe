package kr.kernel.teachme.domain.crawler.service;

import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureUpdateResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class FastcampusLectureDetailCrawlingService implements  LectureDetailCrawlingService{
    private final LectureRepository lectureRepository;
    private final RestTemplate restTemplate;
    private static final String PLATFORM = "fastcampus";

    @Value("${url.fastcampus.detail}")
    String BASE_URL;

    @Override
    public void runCrawler() throws InterruptedException {
        List<Lecture> lecturesToUpdate = fetchLecturesToUpdate();
        List<Lecture> updatedLectures = updateLectureDetails(lecturesToUpdate);
        saveUpdatedLectures(updatedLectures);
    }

    @Override
    public List<Lecture> fetchLecturesToUpdate() {
        return lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc(PLATFORM);
    }

    @Override
    public List<Lecture> updateLectureDetails(List<Lecture> lectures) throws InterruptedException {
        List<Lecture> updatedLectures = new ArrayList<>();

        for (Lecture lecture : lectures){
            FastcampusLectureDetailResponse detailResponse = fetchLectureDetail(lecture);
            FastcampusLectureUpdateResponse updateResponse = mapToLectureUpdateResponse(detailResponse);
            lecture.updateFastcampusDetailInfo(updateResponse);
            updatedLectures.add(lecture);
            Thread.sleep(1000);
        }
        return updatedLectures;
    }

    @Override
    public void saveUpdatedLectures(List<Lecture> lectures) {
        lectureRepository.saveAll(lectures);
    }

    private FastcampusLectureDetailResponse fetchLectureDetail(Lecture lecture) {
        String detailUrl = BASE_URL + lecture.getLectureId() + "/products";
        return getResponseObject(detailUrl, FastcampusLectureDetailResponse.class);
    }

    private <T> T getResponseObject(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }

    private FastcampusLectureUpdateResponse mapToLectureUpdateResponse(FastcampusLectureDetailResponse response) {
        ModelMapper modelMapper = configureModelMapper();
        FastcampusLectureUpdateResponse updateResponse = modelMapper.map(response.getData().getCourse(), FastcampusLectureUpdateResponse.class);

        if (response.getData().getProducts() != null && !response.getData().getProducts().isEmpty()) {
            FastcampusLectureDetailResponse.Products firstProduct = response.getData().getProducts().get(0);
            updateResponse.setPrice(firstProduct.getListPrice());
            updateResponse.setDiscountPrice(firstProduct.getSalePrice());
        }

        return updateResponse;
    }

    private ModelMapper configureModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Converter<String, LocalDateTime> stringToLocalDateTime = context ->
                ZonedDateTime.parse(context.getSource(), DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime();
        modelMapper.addConverter(stringToLocalDateTime);

        PropertyMap<FastcampusLectureDetailResponse.Products, FastcampusLectureUpdateResponse> productMap = new PropertyMap<>() {
            protected void configure() {
                map().setPrice(source.getListPrice());
                map().setDiscountPrice(source.getSalePrice());
            }
        };
        modelMapper.addMappings(productMap);

        return modelMapper;
    }

//    private List<Lecture> getFastcampusDetailResponse(List<Lecture> fastcampusLectures) throws InterruptedException {
//        List<Lecture> fastcampusLectureList = new ArrayList<>();
//        List<Lecture> deleteLectureList = new ArrayList<>();
//
//        for (Lecture lecture : fastcampusLectures){
//            String detailUrl = BASE_URL + lecture.getLectureId() + "/products";
//            FastcampusLectureDetailResponse response = getDetailResponse(detailUrl);
//
//            if(response.getData().getProducts().isEmpty()) {
//                deleteLectureList.add(lecture);
//                continue;
//            }
//
//            FastcampusLectureUpdateResponse updateResponse = convertToLectureUpdateResponse(response);
//
//            lecture.updateFastcampusDetailInfo(updateResponse);
//            fastcampusLectureList.add(lecture);
//
//            Thread.sleep(1000);
//            if(fastcampusLectureList.size() > 10) break;
//        }
//        deleteNoLectureList(deleteLectureList);
//        return fastcampusLectureList;
//    }
//
//    private void deleteNoLectureList(List<Lecture> lectureList) {
//        lectureRepository.deleteAll(lectureList);
//    }
//
//    private FastcampusLectureDetailResponse getDetailResponse(String url) {
//        RestTemplate restTemplate = new RestTemplate();
//        return restTemplate.getForObject(url,FastcampusLectureDetailResponse.class);
//    }
//
//
//    public void update(){
//        if (!lectureRepository.existsByDetailUploadFlagIsFalseAndPlatform(platform)) throw new CrawlerException("업데이트 할 데이터가 없습니다.");
//        List<Lecture> fastcampusLectures = lectureRepository.findAllByDetailUploadFlagIsFalseAndPlatform(platform);
//        try {
//            List<Lecture> fastcampusLectureList = getFastcampusDetailResponse(fastcampusLectures);
//            lectureRepository.saveAll(fastcampusLectureList);
//        }catch (InterruptedException e){
//            throw new CrawlerException("크롤링 중 에러 발생", e);
//        }
//    }
//
//    private FastcampusLectureUpdateResponse convertToLectureUpdateResponse(FastcampusLectureDetailResponse response) {
//        ModelMapper modelMapper = new ModelMapper();
//
//        Converter<String, LocalDateTime> toLocalDateTime = context -> ZonedDateTime.parse(context.getSource(), DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime();
//
//        modelMapper.addConverter(toLocalDateTime);
//
//        return getFastcampusLectureUpdateResponse(response, modelMapper);
//    }
//
//    private static FastcampusLectureUpdateResponse getFastcampusLectureUpdateResponse(FastcampusLectureDetailResponse response, ModelMapper modelMapper) {
//        FastcampusLectureUpdateResponse updateResponse = modelMapper.map(response.getData().getCourse(), FastcampusLectureUpdateResponse.class);
//
//        if (!response.getData().getProducts().isEmpty()) {
//            FastcampusLectureDetailResponse.Products product = response.getData().getProducts().get(0);
//            updateResponse.setPrice(product.getListPrice());
//            updateResponse.setDiscountPrice(product.getSalePrice());
//        }
//
//        return updateResponse;
//    }

}
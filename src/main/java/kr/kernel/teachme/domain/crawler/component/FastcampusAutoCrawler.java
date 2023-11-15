package kr.kernel.teachme.domain.crawler.component;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FastcampusAutoCrawler implements AutoCrawler{

    private final LectureRepository lectureRepository;
    private final RestTemplate restTemplate;
    private static final String PLATFORM = "fastcampus";

    @Value("${url.fastcampus.detail}")
    String BASE_URL;

    @Override
    @Scheduled(cron = "0 0 0/1 * * *")
    public void crawlLectureAutomatically() throws InterruptedException {
        log.info("패캠 크론잡 실행");
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



}

package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureUpdateResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
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
    private static final String PLATFORM = "fastcampus";

    @Value("${url.fastcampus.detail}")
    String BASE_URL;

    @Override
    @Scheduled(cron = "0 0 0/1 * * *")
    public void crawlLectureAutomatically() throws InterruptedException {
        log.info("패캠 크론잡 실행");
        List<Lecture> fastcampusLectures = getLectureToUpdate();
        List<Lecture> fastcampusLectureList = getDetailResponse(fastcampusLectures);
        lectureRepository.saveAll(fastcampusLectureList);
    }

    @Override
    public List<Lecture> getLectureToUpdate() {
        return lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc(PLATFORM);
    }

    @Override
    public List<Lecture> getDetailResponse(List<Lecture> fastcampusLectures) throws InterruptedException {
        List<Lecture> fastcampusLectureList = new ArrayList<>();

        for (Lecture lecture : fastcampusLectures){
            String detailUrl = BASE_URL + lecture.getLectureId() + "/products";
            FastcampusLectureDetailResponse response = getResponseObject(detailUrl);

            FastcampusLectureUpdateResponse updateResponse = convertToLectureUpdateResponse(response);

            lecture.updateFastcampusDetailInfo(updateResponse);
            fastcampusLectureList.add(lecture);

            Thread.sleep(1000);
        }
        return fastcampusLectureList;
    }

    private FastcampusLectureDetailResponse getResponseObject(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url,FastcampusLectureDetailResponse.class);
    }

    private FastcampusLectureUpdateResponse convertToLectureUpdateResponse(FastcampusLectureDetailResponse response) {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDateTime> toLocalDateTime = context -> ZonedDateTime.parse(context.getSource(), DateTimeFormatter.ISO_DATE_TIME).toLocalDateTime();

        modelMapper.addConverter(toLocalDateTime);

        return getFastcampusLectureUpdateResponse(response, modelMapper);
    }

    private static FastcampusLectureUpdateResponse getFastcampusLectureUpdateResponse(FastcampusLectureDetailResponse response, ModelMapper modelMapper) {
        FastcampusLectureUpdateResponse updateResponse = modelMapper.map(response.getData().getCourse(), FastcampusLectureUpdateResponse.class);

        if (!response.getData().getProducts().isEmpty()) {
            FastcampusLectureDetailResponse.Products product = response.getData().getProducts().get(0);
            updateResponse.setPrice(product.getListPrice());
            updateResponse.setDiscountPrice(product.getSalePrice());
        }

        return updateResponse;
    }

}

package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.FastcampusLectureDetailResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FastcampusAutoCrawler implements AutoCrawler{

    private final LectureRepository lectureRepository;
    private static final String PLATFORM = "fastcampus";

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
        String baseUrl = "https://fastcampus.co.kr/.api/www/courses/";

        for (Lecture lecture : fastcampusLectures){
            String detailUrl = baseUrl + lecture.getLectureId() + "/products";
            FastcampusLectureDetailResponse response = getDetailResponse(detailUrl);

            List<FastcampusLectureDetailResponse.Products> productsList = response.getData().getProducts();

            FastcampusLectureDetailResponse.Products products = productsList.get(0);

            int listPrice = products.getListPrice();
            int salePrice = products.getSalePrice();
            String instructor = response.getData().getCourse().getInstructor();
            int totalClassHours = response.getData().getCourse().getTotalClassHours();

            lecture.updateFastcampusDetailInfo(listPrice, salePrice, instructor, totalClassHours * 60);
            fastcampusLectureList.add(lecture);

            Thread.sleep(1000);

        }
        return fastcampusLectureList;
    }

    private FastcampusLectureDetailResponse getDetailResponse(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url,FastcampusLectureDetailResponse.class);
    }




}

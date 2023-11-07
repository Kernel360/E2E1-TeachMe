package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.InflearnLectureDetailResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InflearnAutoCrawler implements AutoCrawler{

    private final String PLATFORM = "inflearn";
    @Autowired
    private LectureRepository lectureRepository;

    @Override
    @Scheduled(cron = "0 0 0/1 * * *")
    public void crawlLectureAutomatically() throws IOException, ParseException, InterruptedException {
        log.info("인프런 크론잡 실행");
        List<Lecture> updateList = getLectureToUpdate();
        try {
            updateList = getDetailResponse(updateList);
            lectureRepository.saveAll(updateList);
        } catch (Exception e) {
            throw new CrawlerException("크롤링 중 에러 발생", e);
        }
    }

    @Override
    public List<Lecture> getLectureToUpdate() {
        return lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc(PLATFORM);
    }

    @Override
    public List<Lecture> getDetailResponse(List<Lecture> lectures) throws InterruptedException, IOException, ParseException {
        List<Lecture> updatedList = new ArrayList<>();
        for (Lecture lecture : lectures) {
            InflearnLectureDetailResponse detailResponse = crawlInflearnLectureDetail(lecture);
            lecture.updateInflearnDetailInfo(detailResponse.getDuration(), detailResponse.getImageSource(), detailResponse.getPostDate(), detailResponse.getUpdateDate());
            updatedList.add(lecture);
        }
        return updatedList;
    }


    private InflearnLectureDetailResponse crawlInflearnLectureDetail(Lecture lecture) throws IOException, ParseException {
        String pageUrl = lecture.getUrl();
        InflearnLectureDetailResponse response = new InflearnLectureDetailResponse();

        Connection conn = Jsoup.connect(pageUrl);
        Document doc = conn.get();
        Elements pageElements = doc.select("div.cd-header__title-container");

        String title = pageElements.select("h1").text();

        Elements elements = doc.select("div.cd-floating__info > div:nth-child(2)");
        Elements imageElements = doc.select("div.cd-header__thumbnail");
        Elements postElements = doc.select("div.cd-date__content");

        String info = "총 0개 수업 (0시간 0분)";
        if(!elements.isEmpty()) {
            info = elements.get(0).text();
        } else {
            response.setDeletedFlag(true);
        }

        String imageSource = imageElements.select("img").attr("src");

        String postDateString = postElements.select("span.cd-date__published-date").text();
        String updateDateString = postElements.select("span.cd-date__last-updated-at").text();

        response.setInflearnInfoToData(info);
        response.setTitle(title);
        response.setImageSource(imageSource);
        response.setDateFromString(postDateString, updateDateString);
        return response;
    }





}

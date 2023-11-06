package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.domain.crawler.dto.InflearnLectureDetailResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InfleanAutoCrawler implements AutoCrawler{

    private final String PLATFORM = "inflearn";
    @Autowired
    private LectureRepository lectureRepository;

    @Override
    public void crawlLectureAutomatically() {

    }

    @Override
    public List<Lecture> getLectureToUpdate() {
        return lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc(PLATFORM);
    }

    private List<Lecture> updateLectureDetail(List<Lecture> targetList) throws Exception {
        List<Lecture> updatedList = new ArrayList<>();
        int crawlLimit = 0;
        for (Lecture lecture : targetList) {
            crawlLimit++;
            InflearnLectureDetailResponse detailResponse = crawlInflearnLectureDetail(lecture);
            lecture.updateInflearnDetailInfo(detailResponse.getDuration(), detailResponse.getImageSource(), detailResponse.getPostDate(), detailResponse.getUpdateDate());
            updatedList.add(lecture);
            if(crawlLimit == 10) break;
        }
        return updatedList;
    }

    private InflearnLectureDetailResponse crawlInflearnLectureDetail(Lecture lecture) throws IOException, ParseException {
        String pageUrl = lecture.getUrl();
        InflearnLectureDetailResponse response = new InflearnLectureDetailResponse();

        Connection conn = Jsoup.connect(pageUrl);
        Document doc = conn.get();
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
        String updateDateString = postElements.select("span.cd-date__updated-at").text();

        response.setInflearnInfoToData(info);
        response.setImageSource(imageSource);
        response.setDateFromString(postDateString, updateDateString);
        return response;
    }





}

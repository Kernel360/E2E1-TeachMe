package kr.kernel.teachme.domain.crawler.service;

import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.domain.crawler.dto.InflearnLectureDetailResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InflearnLectureDetailCrawlingService {
	private final LectureRepository lectureRepository;

	private static final String platform = "inflearn";
	private List<Lecture> getLectureListNotUpdated() {
		return lectureRepository.findAllByDetailUploadFlagIsFalseAndPlatform(platform);
	}

	private void deleteNoLectureData(List<Lecture> lectureList) {
		lectureRepository.deleteAll(lectureList);
	}
	private List<Lecture>  updateLectureDetail(List<Lecture> targetList) throws Exception {
		List<Lecture> updatedList = new ArrayList<>();
		List<Lecture> deletedList = new ArrayList<>();
		int crawlLimit = 0;
		for (Lecture lecture : targetList) {
			crawlLimit++;
			InflearnLectureDetailResponse detailResponse = crawlInflearnLectureDetail(lecture);
			if (detailResponse.isDeletedFlag()) {
				deletedList.add(lecture);
				continue;
			}
			lecture.updateInflearnDetailInfo(detailResponse.getDuration(), detailResponse.getImageSource(), detailResponse.getPostDate(), detailResponse.getUpdateDate());
			updatedList.add(lecture);
			if(crawlLimit == 50) break;
		}
		deleteNoLectureData(deletedList);
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
		String updateDateString = postElements.select("span.cd-date__last-updated-at").text();

		response.setInflearnInfoToData(info);
		response.setImageSource(imageSource);
		response.setDateFromString(postDateString, updateDateString);
		return response;
	}

	public void runInflearnLectureDetailCrawler() {
		if (!lectureRepository.existsByDetailUploadFlagIsFalseAndPlatform(platform)) throw new CrawlerException("업데이트할 데이터가 없습니다.");
		List<Lecture> updateList = getLectureListNotUpdated();
		try {
			updateList = updateLectureDetail(updateList);
			lectureRepository.saveAll(updateList);
		} catch (Exception e) {
			throw new CrawlerException("크롤링 중 에러 발생", e);
		}
	}
}

package kr.kernel.teachme.domain.crawler.service;

import kr.kernel.teachme.common.annotation.LogExecutionTime;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class InflearnLectureDetailCrawlingService implements LectureDetailCrawlingService {

	private final LectureRepository lectureRepository;
	private static final String PLATFORM = "inflearn";

	@Override
	@LogExecutionTime("인프런 상세 크롤링 실행")
	public void runCrawler() {
		try {
			List<Lecture> lecturesToUpdate = fetchLecturesToUpdate();
			List<Lecture> updatedLectures = updateLectureDetails(lecturesToUpdate);
			saveUpdatedLectures(updatedLectures);
		} catch (Exception e) {
			log.error("크롤링 중 에러 발생", e);
			throw new CrawlerException("크롤링 중 에러 발생", e);
		}
	}

	@Override
	public List<Lecture> fetchLecturesToUpdate() {
		return lectureRepository.findTop10ByPlatformOrderByLastCrawlDateAsc(PLATFORM);
	}

	@Override
	public List<Lecture> updateLectureDetails(List<Lecture> lectures) throws InterruptedException, IOException, ParseException {
		List<Lecture> updatedList = new ArrayList<>();
		for (Lecture lecture : lectures) {
			try {
				InflearnLectureDetailResponse detailResponse = crawlInflearnLectureDetail(lecture);
				updateLectureInformation(lecture, detailResponse);
				updatedList.add(lecture);
			} catch (IOException | ParseException e) {
				log.error("강의 상세 정보 크롤링 중 에러 발생", e);
			}
		}
		return updatedList;
	}

	@Override
	public void saveUpdatedLectures(List<Lecture> lectures) {
		lectureRepository.saveAll(lectures);
	}

	private void updateLectureInformation(Lecture lecture, InflearnLectureDetailResponse detailResponse) {
		lecture.updateInflearnDetailInfo(
				detailResponse.getDuration(),
				detailResponse.getImageSource(),
				detailResponse.getPostDate(),
				detailResponse.getUpdateDate()
		);
	}

	private InflearnLectureDetailResponse crawlInflearnLectureDetail(Lecture lecture) throws IOException, ParseException {
		Document doc = connectToLecturePage(lecture.getUrl());
		return parseLecturePage(doc);
	}

	private Document connectToLecturePage(String pageUrl) throws IOException {
		Connection conn = Jsoup.connect(pageUrl);
		return conn.get();
	}

	private InflearnLectureDetailResponse parseLecturePage(Document doc) throws ParseException {
		InflearnLectureDetailResponse response = new InflearnLectureDetailResponse();

		extractTitle(doc, response);
		extractInfo(doc, response);
		extractImageSource(doc, response);
		extractDates(doc, response);

		return response;
	}

	private void extractTitle(Document doc, InflearnLectureDetailResponse response) {
		Elements pageElements = doc.select("div.cd-header__title-container");
		String title = pageElements.select("h1").text();
		response.setTitle(title);
	}

	private void extractInfo(Document doc, InflearnLectureDetailResponse response) {
		Elements elements = doc.select("div.cd-floating__info > div:nth-child(2)");
		String info = elements.isEmpty() ? "총 0개 수업 (0시간 0분)" : elements.get(0).text();
		response.setInflearnInfoToData(info);
	}

	private void extractImageSource(Document doc, InflearnLectureDetailResponse response) {
		Elements imageElements = doc.select("div.cd-header__thumbnail");
		String imageSource = imageElements.select("img").attr("src");
		response.setImageSource(imageSource);
	}

	private void extractDates(Document doc, InflearnLectureDetailResponse response) throws ParseException {
		Elements postElements = doc.select("div.cd-date__content");
		String postDateString = postElements.select("span.cd-date__published-date").text();
		String updateDateString = postElements.select("span.cd-date__last-updated-at").text();
		response.setDateFromString(postDateString, updateDateString);
	}

}

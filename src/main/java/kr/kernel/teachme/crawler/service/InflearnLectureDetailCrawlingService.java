package kr.kernel.teachme.crawler.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import kr.kernel.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.crawler.dto.InflearnLectureDetailResponse;
import kr.kernel.teachme.crawler.entity.InflearnLecture;
import kr.kernel.teachme.crawler.repository.InflearnRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InflearnLectureDetailCrawlingService {
	private final InflearnRepository inflearnRepository;
	private final LectureRepository lectureRepository;
	private List<InflearnLecture> getLectureListNotUpdated() {
		return inflearnRepository.findAllByDetailUploadFlagIsFalse();
	}

	private void deleteBootCamp(InflearnLecture lecture) {
		lectureRepository.deleteByUrl(lecture.getUrl());
		inflearnRepository.delete(lecture);
	}
	private List<InflearnLecture>  updateLectureDetail(List<InflearnLecture> targetList) throws Exception {
		List<InflearnLecture> updatedList = new ArrayList<>();
		int crawlLimit = 0;
		for (InflearnLecture lecture : targetList) {
			crawlLimit++;
			InflearnLectureDetailResponse detailResponse = crawlInflearnLectureDetail(lecture);
			if (detailResponse.isDeletedFlag()) {
				deleteBootCamp(lecture);
				continue;
			}
			lecture.updateDetailInfo(detailResponse.getVideoCnt(), detailResponse.getDuration(), detailResponse.getImageSource(), detailResponse.getPostDate(), detailResponse.getUpdateDate());
			updatedList.add(lecture);
			if(crawlLimit > 300) break;
		}
		return updatedList;
	}

	private InflearnLectureDetailResponse crawlInflearnLectureDetail(InflearnLecture lecture) throws IOException, ParseException {
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

	public void runInflearnLectureDetailCrawler() {
		if (!inflearnRepository.existsByDetailUploadFlagIsFalse()) throw new CrawlerException("업데이트할 데이터가 없습니다.");
		List<InflearnLecture> updateList = getLectureListNotUpdated();
		try {
			updateList = updateLectureDetail(updateList);
		} catch (Exception e) {
			throw new CrawlerException("크롤링 중 에러 발생", e);
		}
		inflearnRepository.saveAll(updateList);
	}
}

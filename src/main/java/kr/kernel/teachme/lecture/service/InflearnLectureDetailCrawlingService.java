package kr.kernel.teachme.lecture.service;

import java.io.IOException;
import java.util.List;

import javax.lang.model.util.Elements;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import io.swagger.models.auth.In;
import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.lecture.dto.InflearnLectureDetailResponse;
import kr.kernel.teachme.lecture.dto.InflearnLectureListResponse;
import kr.kernel.teachme.lecture.entity.InflearnLecture;
import kr.kernel.teachme.lecture.repository.InflearnRepository;
import kr.kernel.teachme.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InflearnLectureDetailCrawlingService {
	private final InflearnRepository inflearnRepository;
	private final LectureRepository lectureRepository;

	private List<InflearnLecture> getLectureListNotUpdated() {
		return inflearnRepository.findAllByDetailUploadFlagIsFalse();
	}
	private void updateLectureDetail(List<InflearnLecture> updateList) {
		InflearnLectureDetailResponse inflearnLectureDetailResponse = new InflearnLectureDetailResponse();

		for (InflearnLecture lecture : updateList) {
			String pageUrl = lecture.getUrl();

		}

	}
	private void crawlInflearnLectureDetail(List<InflearnLecture> lecture, String pageUrl) throws
		IOException {
		Connection conn = Jsoup.connect(pageUrl);
		Document doc = conn.get();

		InflearnLectureDetailResponse inflearnLectureDetailResponse = new InflearnLectureDetailResponse();

		//inflearnLectureDetailResponse.setVideoCnt(doc.select("cd-floating__info > nth-child(2)"));
	}
	public void runInflearnLectureDetailCrawler() {
		if (!inflearnRepository.existsByDetailUploadFlagIsFalse()) throw new CrawlerException("업데이트할 데이터가 없습니다.");
		List<InflearnLecture> updateList = getLectureListNotUpdated();


	}
}

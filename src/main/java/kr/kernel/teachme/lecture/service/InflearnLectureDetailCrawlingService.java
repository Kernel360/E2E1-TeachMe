package kr.kernel.teachme.lecture.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.lecture.dto.InflearnLectureDetailResponse;
import kr.kernel.teachme.lecture.entity.InflearnLecture;
import kr.kernel.teachme.lecture.repository.InflearnRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InflearnLectureDetailCrawlingService {
	private final InflearnRepository inflearnRepository;

	private List<InflearnLecture> getLectureListNotUpdated() {
		return inflearnRepository.findAllByDetailUploadFlagIsFalse();
	}

	private List<InflearnLecture>  updateLectureDetail(List<InflearnLecture> targetList) throws IOException {
		List<InflearnLecture> updatedList = new ArrayList<>();
		int crawlLimit = 0;
		for (InflearnLecture lecture : targetList) {
			crawlLimit++;
			InflearnLectureDetailResponse detailResponse = crawlInflearnLectureDetail(lecture.getUrl());
			lecture.updateDetailInfo(detailResponse.getVideoCnt(), detailResponse.getDuration(), detailResponse.getImageSource());
			updatedList.add(lecture);
			if(crawlLimit > 50) break;
		}
		return updatedList;
	}

	private InflearnLectureDetailResponse crawlInflearnLectureDetail(String pageUrl) throws IOException {
		Connection conn = Jsoup.connect(pageUrl);
		Document doc = conn.get();
		Elements elements = doc.select("div.cd-floating__info > div:nth-child(2)");
		Elements imageElements = doc.select("div.cd-header__thumbnail");

		String info = "총 0개 수업 (0시간 0분)";
		if(!elements.isEmpty()) {
			info = elements.get(0).text();
		} else {
			System.out.println(pageUrl);
		}

		String imageSource = imageElements.select("img").attr("src");

		InflearnLectureDetailResponse response = new InflearnLectureDetailResponse();
		response.setInflearnInfoToData(info);
		response.setImageSource(imageSource);
		return response;
	}
}

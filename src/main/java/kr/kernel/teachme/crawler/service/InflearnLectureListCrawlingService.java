package kr.kernel.teachme.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kr.kernel.teachme.exception.CrawlerException;
import kr.kernel.teachme.crawler.dto.InflearnLectureListResponse;
import kr.kernel.teachme.lecture.entity.Lecture;
import kr.kernel.teachme.lecture.repository.LectureRepository;
import kr.kernel.teachme.lecture.util.StringUtil;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InflearnLectureListCrawlingService {

	private final LectureRepository lectureRepository;

	private List<InflearnLectureListResponse> crawlInflearnLectureList() throws IOException {
		String targetUrl = "https://www.inflearn.com/courses";
		Connection conn = Jsoup.connect(targetUrl);
		Document doc = conn.get();
		int pageNum = getPageNumFromInflearn(doc);

		if(pageNum == 0) return Collections.emptyList();

		List<InflearnLectureListResponse> crawledDataList = new ArrayList<>();

		for(var i = 1; i <= pageNum; i++) {
			String url = "https://www.inflearn.com/courses?order=seq&page=";
			String pageUrl = url + i;
			Connection pageConn = Jsoup.connect(pageUrl);
			Document document = pageConn.get();
			Elements courseElements = document.select("div.course");

			for (Element course : courseElements) {
				InflearnLectureListResponse inflearnCourse = buildCourseToObject(course);
				crawledDataList.add(inflearnCourse);
			}
		}

		return crawledDataList;
	}

	private static int getPageNumFromInflearn(Document doc) {
		Element paginationElement = doc.selectFirst("ul.pagination-list");
		if (paginationElement != null) {
			Elements pageLinks = paginationElement.select("a");
			if (!pageLinks.isEmpty()) {
				Element lastPageLink = pageLinks.last();
                assert lastPageLink != null;
                String lastPageText = lastPageLink.text();
				return Integer.parseInt(lastPageText);
			}
		}
		return 0;
	}

	private InflearnLectureListResponse buildCourseToObject(Element course) {
		InflearnLectureListResponse inflearnCourse = new InflearnLectureListResponse();
		inflearnCourse.setTitle(course.select("div.course_title").text());
		inflearnCourse.setImageSource(course.select("figure.is_thumbnail > img").attr("src"));
		inflearnCourse.setInstructor(course.select("div.instructor").text());

		if(!course.select("div.price > del").isEmpty()) {
			inflearnCourse.setRealIntPrice(Integer.parseInt(StringUtil.removeNotNumeric(course.select("div.price > del")
					.text())));
			inflearnCourse.setSaleIntPrice(Integer.parseInt(StringUtil.removeNotNumeric(course.select("div.price > span.pay_price")
					.text())));
		} else {
			int price = StringUtil.isNumeric(StringUtil.removeNotNumeric(course.select("div.price").text()))
					? Integer.parseInt(StringUtil.removeNotNumeric(course.select("div.price").text())) : 0;
			inflearnCourse.setRealIntPrice(price);
			inflearnCourse.setSaleIntPrice(price);
		}

		String masterUrl = "https://www.inflearn.com";
		inflearnCourse.setUrl(masterUrl + course.select("a.e_course_click").attr("href"));
		inflearnCourse.setDescription(course.select("p.course_description").text());
		inflearnCourse.setSkills(course.select("div.course_skills > span").text());

		return inflearnCourse;
	}

	public void saveCrawledData(List<InflearnLectureListResponse> crawledData) {
		List<Lecture> lectureList = new ArrayList<>();
		for(InflearnLectureListResponse data : crawledData) {
			lectureList.add(data.toEntity());
		}
		lectureRepository.saveAll(lectureList);
	}

	public boolean isAtLeastOneRowExists() {
		return lectureRepository.count() > 0;
	}

	public void runInflearnLectureCrawler() {
		if(isAtLeastOneRowExists()) throw new CrawlerException("크롤링 불가 상태");
		List<InflearnLectureListResponse> crawledDataList = null;
		try {
			crawledDataList = crawlInflearnLectureList();
		} catch (IOException e) {
			throw new CrawlerException("크롤링 중 에러 발생", e);
		}
		saveCrawledData(crawledDataList);
	}
}

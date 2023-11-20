package kr.kernel.teachme.domain.crawler.service;

import kr.kernel.teachme.common.annotation.LogExecutionTime;
import kr.kernel.teachme.common.exception.CrawlerException;
import kr.kernel.teachme.common.util.StringUtil;
import kr.kernel.teachme.domain.crawler.dto.InflearnLectureListResponse;
import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InflearnLectureListCrawlingService implements LectureListCrawlingService<InflearnLectureListResponse> {

	private final LectureRepository lectureRepository;
	private static final String PLATFORM = "inflearn";
	@Value("${url.inflearn.courses}")
	String TARGET_URL;
	@Value("${url.inflearn.page}")
	String PAGE_URL;


	@Override
	@LogExecutionTime("인프런 목록 크롤링 실행")
	public List<InflearnLectureListResponse> crawlData() throws IOException {
		Document doc = getDocument(TARGET_URL);
		int pageNum = getPageNumFromInflearn(doc);

		if(pageNum == 0) return Collections.emptyList();

		return getCourseForAllPages(pageNum);
	}

	private Document getDocument(String url) throws IOException {
		Connection conn = Jsoup.connect(url);
		return conn.get();
	}

	private List<InflearnLectureListResponse> getCourseForAllPages(int pageNum) throws IOException {
		List<InflearnLectureListResponse> crawledDataList = new ArrayList<>();
		for(var i = 1; i <= pageNum; i++) {
			crawledDataList.addAll(getCoursesForPage(i));
		}
		return crawledDataList;
	}

	private List<InflearnLectureListResponse> getCoursesForPage(int pageNumber) throws IOException {
		String pageUrl = PAGE_URL + pageNumber;
		Document pageDoc = getDocument(pageUrl);
		Elements courseElements = pageDoc.select("div.course");

		List<InflearnLectureListResponse> courses = new ArrayList<>();
		for(Element course : courseElements) {
			courses.add(buildCourseToObject(course));
		}
		return courses;
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

	@Override
	public void saveCrawledData(List<InflearnLectureListResponse> crawledData) {
		List<Lecture> lectureList = new ArrayList<>();
		for(InflearnLectureListResponse data : crawledData) {
			lectureList.add(data.toEntity());
		}
		lectureRepository.saveAll(lectureList);
	}


	@Override
	public boolean isAtLeastOneRowExists() {
		return lectureRepository.countByPlatform(PLATFORM) > 0;
	}

	@Override
	public void runCrawler() {
		if(isAtLeastOneRowExists()) throw new CrawlerException("크롤링 불가 상태");
		try {
			List<InflearnLectureListResponse> crawledDataList = crawlData();
			saveCrawledData(crawledDataList);
		} catch (IOException e) {
			throw new CrawlerException("크롤링 중 에러 발생", e);
		}
	}
}

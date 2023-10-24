package kr.kernel360.teachme.lecture.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kr.kernel360.teachme.lecture.util.StringUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.kernel360.teachme.lecture.dto.InflearnResponseDto;

public class InflearnLectureListCrawlingService {

	private final static String MASTER_URL = "https://www.inflearn.com";
	private final static String TARGET_URL = "https://www.inflearn.com/courses";

	public static void main(String[] args) throws IOException {

		Connection conn = Jsoup.connect(TARGET_URL);
		Document doc = conn.get();
		int pageNum = getPageNumFromInflearn(doc);

		if(pageNum == 0) return;

		List<InflearnResponseDto> crawledDataList = new ArrayList<>();

		for(var i = 1; i <= pageNum; i++) {
			String pageUrl = "https://www.inflearn.com/courses?order=seq&page=" + i;
			Connection pageConn = Jsoup.connect(pageUrl);
			Document document = pageConn.get();
			Elements courseElements = document.select("div.course");

            for (Element course : courseElements) {
				InflearnResponseDto inflearnCourse = new InflearnResponseDto();
				inflearnCourse.setTitle(course.select("div.course_title").text());
				inflearnCourse.setImageSource(course.select("figure.is_thumbnail > img").attr("src"));
				inflearnCourse.setStudentCnt(course.select("span.student_cnt").text());
				inflearnCourse.setInstructor(course.select("div.instructor").text());

				if(course.select("div.price > del").size() > 0) {
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

				inflearnCourse.setUrl(MASTER_URL + course.select("a.e_course_click").attr("href"));
				inflearnCourse.setDescription(course.select("p.course_description").text());
				inflearnCourse.setDifficulty(course.select("div.course_level > span").text());
				inflearnCourse.setSkills(course.select("div.course_skills > span").text());

				crawledDataList.add(inflearnCourse);
            }

			if(crawledDataList.size() > 10) break;

		}
		System.out.println(crawledDataList.get(1).getSkills());
	}

	private static int getPageNumFromInflearn(Document doc) {
		Element paginationElement = doc.selectFirst("ul.pagination-list");
		if (paginationElement != null) {
			Elements pageLinks = paginationElement.select("a");
			if (!pageLinks.isEmpty()) {
				Element lastPageLink = pageLinks.last();
				String lastPageText = lastPageLink.text();
				int lastPageNum = Integer.parseInt(lastPageText);
				return lastPageNum;
			}
		}
		return 0;
	}
}
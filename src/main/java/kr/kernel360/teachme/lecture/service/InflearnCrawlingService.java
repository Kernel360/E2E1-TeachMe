package kr.kernel360.teachme.lecture.service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.kernel360.teachme.lecture.dto.InflearnResponseDto;

public class InflearnCrawlingService {
	public static void main(String[] args) {

		final String inflearnUrl = "https://www.inflearn.com/courses/it-programming";
		Connection conn = Jsoup.connect(inflearnUrl);

		try {
			Document document = conn.get();
			Elements titleElements = document.select("div.card-content > div.course_title");
			Elements idElements = document.select("a.course_card_front.e_course_click");
			Elements priceElements = document.getElementsByClass("price");
			Elements instructorElements = document.getElementsByClass("instructor");
			Elements descriptionElements = document.select("p.course_description");
			Elements skillElements = document.select("div.course_skills > span");

			for (int j = 0; j < priceElements.size(); j++) {
				final String title = titleElements.get(j).text();
				final String id = idElements.get(j).attr("abs:href");
				final String price = priceElements.get(j).text();
				final String realPrice = getRealPrice(price);
				final String salePrice = getSalePrice(price);
				final String instructor = instructorElements.get(j).text();
				final String description = descriptionElements.get(j).text();
				final String skills = removeWhiteSpace(skillElements.get(j).text());

				final int realIntPrice = toInt(removeNotNumeric(realPrice));
				final int saleIntPrice = toInt(removeNotNumeric(salePrice));

				InflearnResponseDto inflearnResponseDto =  new InflearnResponseDto(title, id, realIntPrice, saleIntPrice, instructor, description, skills);
				System.out.println(inflearnResponseDto.toString());
				// System.out.println(j + ".강의 제목: " + title);
				// System.out.println(j + ".강의 id: " + id);
				// System.out.println(j + ".가격: " + realIntPrice);
				// System.out.println(j + ".할인 가격: " + saleIntPrice);
				// System.out.println(j + ".강의자: " + instructor);
				// System.out.println(j + ".강의 부가설명: " + description);
				// System.out.println(j + ".기술 스택: " + skills);
				// System.out.println("------------------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static String getRealPrice(final String price) {
		final String[] pricesArray = price.split(" ");
		return pricesArray[0];
	}

	private static String getSalePrice(final String price) {
		final String[] pricesArray = price.split(" ");
		return (pricesArray.length == 1) ? price : pricesArray[1];
	}

	private static String removeNotNumeric(final String str) {
		return str.replaceAll("\\W", "");
	}

	private static int toInt(final String str) {
		return Integer.parseInt(str);
	}

	private static String removeWhiteSpace(final String str) {
		return str.replaceAll("\\s", "");
	}
}

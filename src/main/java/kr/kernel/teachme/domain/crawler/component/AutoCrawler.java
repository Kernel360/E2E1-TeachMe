package kr.kernel.teachme.domain.crawler.component;

import kr.kernel.teachme.domain.lecture.entity.Lecture;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AutoCrawler {

    void crawlLectureAutomatically() throws InterruptedException, IOException, ParseException;

    List<Lecture> fetchLecturesToUpdate();

    List<Lecture> updateLectureDetails(List<Lecture> lectures) throws InterruptedException, IOException, ParseException;

    void saveUpdatedLectures(List<Lecture> lectures);
}

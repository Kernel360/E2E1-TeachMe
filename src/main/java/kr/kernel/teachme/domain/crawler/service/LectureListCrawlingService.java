package kr.kernel.teachme.domain.crawler.service;

import java.io.IOException;
import java.util.List;

public interface LectureListCrawlingService<T> {
    List<T> crawlData() throws IOException;
    boolean isAtLeastOneRowExists();
    void saveCrawledData(List<T> crawledData);
    void runCrawler();
}

package kr.kernel.teachme.domain.report.component;

import kr.kernel.teachme.domain.lecture.entity.Lecture;
import kr.kernel.teachme.domain.lecture.repository.LectureRepository;
import kr.kernel.teachme.domain.report.entity.Report;
import kr.kernel.teachme.domain.report.repository.ReportRepository;
import kr.kernel.teachme.domain.review.entity.Review;
import kr.kernel.teachme.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.kernel.teachme.common.util.DateUtil.convertLocalDateToDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewStatisticsScheduler {

    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;
    private final LectureRepository lectureRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void calculateDailyReviewStatistics() {
        log.info("레포트 생성 시작");
        LocalDate yesterday = getYesterdayDate();
        List<Review> yesterdayReviews = getReviewsByDate(yesterday);

        Map<Long, List<Review>> reviewByLecture = groupReviewsByLecture(yesterdayReviews);

        reviewByLecture.forEach(this::generateAndSaveReport);
        log.info("레포트 생성 완료");
    }

    private LocalDate getYesterdayDate() {
        return LocalDate.now().minusDays(1);
    }

    private List<Review> getReviewsByDate(LocalDate date) {
        return reviewRepository.findByCreateDate(convertLocalDateToDate(date));
    }

    private Map<Long, List<Review>> groupReviewsByLecture(List<Review> reviews) {
        return reviews.stream()
                .collect(Collectors.groupingBy(review -> review.getLecture().getId()));
    }

    private void generateAndSaveReport(Long lectureId, List<Review> reviews) {
        Lecture lecture = findLectureById(lectureId);
        double averageScore = calculateAverageScore(reviews);
        Report report = buildReport(lecture, reviews, averageScore);
        reportRepository.save(report);
    }

    private Lecture findLectureById(Long lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new RuntimeException("Lecture not found: " + lectureId));
    }

    private double calculateAverageScore(List<Review> reviews) {
        return reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);
    }

    private Report buildReport(Lecture lecture, List<Review> reviews, double averageScore) {
        return Report.builder()
                .date(getYesterdayDate())
                .lecture(lecture)
                .reviewCount(reviews.size())
                .averageScore(averageScore)
                .build();
    }
}

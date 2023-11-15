package kr.kernel.teachme.domain.report.controller;

import io.swagger.annotations.ApiOperation;
import kr.kernel.teachme.domain.report.dto.ReviewReportResponse;
import kr.kernel.teachme.domain.report.entity.Report;
import kr.kernel.teachme.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReviewStatisticsController {

    private final ReportRepository reportRepository;

    @ApiOperation(value="총 리뷰 수 통계 요청 반환", notes="리뷰 레포트 데이터 반환")
    @GetMapping("/api/report/statistics")
    public Map<LocalDate, ReviewReportResponse> getReviewStatistics(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Report> reports = reportRepository.findByDateBetween(startDate, endDate);

        return reports.stream()
                .collect(Collectors.toMap(
                        Report::getDate,
                        report -> new ReviewReportResponse(report.getReviewCount(), report.getAverageScore()),
                        (existing, replacement) -> {
                            int totalReviewCount = existing.getReviewCount() + replacement.getReviewCount();
                            double totalAverageScore = (existing.getAverageScore() * existing.getReviewCount() + replacement.getAverageScore() * replacement.getReviewCount()) / totalReviewCount;
                            return new ReviewReportResponse(totalReviewCount, totalAverageScore);
                        }
                ));
    }

    @ApiOperation(value="강의별 평균 평점 통계 요청 반환", notes="리뷰 레포트 데이터 반환")
    @GetMapping("/api/report/average")
    public Map<String, Double> getAverageScoresPerLecture(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Report> reports = reportRepository.findByDateBetween(date, date);

        return reports.stream()
                .collect(Collectors.groupingBy(
                        report -> report.getLecture().getTitle(),
                        Collectors.averagingDouble(Report::getAverageScore)
                ));
    }

    @ApiOperation(value="강의별 리뷰 수 통계 요청 반환", notes="리뷰 레포트 데이터 반환")
    @GetMapping("/api/report/review-count")
    public Map<String, Integer> getReviewCountPerLecture(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Report> reports = reportRepository.findByDateBetween(date, date);

        return reports.stream()
                .collect(Collectors.groupingBy(
                        report -> report.getLecture().getTitle(),
                        Collectors.summingInt(Report::getReviewCount)
                ));
    }
}

package kr.kernel.teachme.domain.report.repository;

import kr.kernel.teachme.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByDateBetween(LocalDate startDate, LocalDate endDate);
}

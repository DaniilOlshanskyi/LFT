package lft.reports;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lft.reports.Reports;

public interface ReportsRepository extends CrudRepository<Reports, Integer> {
	
	Iterable<Reports> findAllByResolveFlag(@Param("report_reportFlag") int reportFlag);
	
}

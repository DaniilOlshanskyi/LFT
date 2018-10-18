package lft.reports;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lft.reports.Reports;

public interface ReportsRepository extends CrudRepository<Reports, Integer> {
	
	Iterable<Reports> findAllByreportResolveFlag(int reportFlag);
	
}

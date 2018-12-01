package lft.demo.reports;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lft.demo.reports.Reports;
@Repository
public interface ReportsRepository extends CrudRepository<Reports, Integer> {
	
	Iterable<Reports> findAllByreportResolveFlag(int reportFlag);
	
}

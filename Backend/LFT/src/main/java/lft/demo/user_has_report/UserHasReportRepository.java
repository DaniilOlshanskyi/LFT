package lft.demo.user_has_report;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lft.demo.user_has_report.UserHasReport;

/**
 * Repository to hold hasReports objects.
 * 
 * @author Mike Ostrow
 *
 */
@Repository
public interface UserHasReportRepository extends CrudRepository<UserHasReport, Integer>{
	
}

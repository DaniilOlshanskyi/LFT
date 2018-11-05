package lft.demo.user_has_report;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import lft.demo.user_has_report.UserHasReport;

public interface UserHasReportRepository extends CrudRepository<UserHasReport, Integer>{
	
}

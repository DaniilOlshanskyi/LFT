package lft.demo.reports;
import org.springframework.web.bind.annotation.RestController;

import lft.demo.Profiles;
import lft.demo.UserRepository;
import lft.demo.user_has_report.UserHasReport;
import lft.demo.user_has_report.UserHasReportRepository;

import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring controller to handle operations with reports.
 * 
 * @author Mike Ostrow
 *
 */
@RestController
public class ReportsController {
	
	@Autowired
    private ReportsRepository reportsRepository;
	
	@Autowired
	private UserHasReportRepository userHasReportsRepository;
	
	/**
	 * Mapping to get all reports.
	 * 
	 * @return all reports.
	 */
	@GetMapping(path="/reports")
    public @ResponseBody Iterable<Reports> getAllreports(){
    	return reportsRepository.findAll();
    }
	
	/**
	 * Mapping to get a report by id.
	 * 
	 * @param id path parameter - report id to look for.
	 * @return the repot object with the given id.
	 */
	@GetMapping(path="/reports/{id}")
    public @ResponseBody Optional<Reports> getReportsById(@PathVariable(value="id") int id){
    	return reportsRepository.findById(id);
    }
	
	
//	@GetMapping(path="/reports/reportId/{reportId}")
//    public @ResponseBody Optional<Reports> getReportsByReportId(@PathVariable(value="reportId") int reportId){
//    	return reportsRepository.findByReportId(reportId);
//    }
	
//	@GetMapping(path="/reports/profId/{profId}")
//    public @ResponseBody Optional<Reports> getReportsByUserId(@PathVariable(value="profId") int profId){
//    	return reportsRepository.findByUserId(profId);
//    }
	
	/**
	 * Mapping to get all resolved/unresolved reports.
	 * 
	 * @param resolveFlag report status to look for.
	 * @return list of reports with the given flag status.
	 */
	@GetMapping(path="/reports/resolutionStatus/{reportResolveFlag}")
    public @ResponseBody Iterable<Reports> getUserByResolved(@PathVariable(value="reportResolveFlag") int resolveFlag){
    	return reportsRepository.findAllByreportResolveFlag(resolveFlag);
    }
	
	/**
	 * Mapping to post a new report.
	 * 
	 * @param report report object to be posted.
	 * @param profId id of the report to be set.
	 * @return report object if successful. 
	 */
	@PostMapping(path="/postReport/{profId}")
	Reports newReport(@RequestBody Reports report, @PathVariable(value="profId") int profId) {
		UserHasReport link = new UserHasReport();
		link.setreportId(report.getId());
		link.setprofId(profId);
		userHasReportsRepository.save(link);
		return reportsRepository.save(report);
	}
}

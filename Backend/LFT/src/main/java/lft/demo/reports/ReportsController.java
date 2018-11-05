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

@RestController
public class ReportsController {
	
	@Autowired
    private ReportsRepository reportsRepository;
	
	@Autowired
	private UserHasReportRepository userHasReportsRepository;
	
	@GetMapping(path="/reports")
    public @ResponseBody Iterable<Reports> getAllreports(){
    	return reportsRepository.findAll();
    }
	
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
	
	@GetMapping(path="/reports/resolutionStatus/{reportResolveFlag}")
    public @ResponseBody Iterable<Reports> getUserByResolved(@PathVariable(value="reportResolveFlag") int resolveFlag){
    	return reportsRepository.findAllByreportResolveFlag(resolveFlag);
    }
	
	@PostMapping(path="/postReport/{profId}")
	Reports newReport(@RequestBody Reports report, @PathVariable(value="profId") int profId) {
		UserHasReport link = new UserHasReport(report.getId(), profId);
		userHasReportsRepository.save(link);
		return reportsRepository.save(report);
	}
}

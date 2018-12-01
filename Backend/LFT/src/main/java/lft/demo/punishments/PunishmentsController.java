package lft.demo.punishments;

import org.springframework.web.bind.annotation.RestController;

import lft.demo.Profiles;
import lft.demo.UserRepository;
import lft.demo.reports.Reports;
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

public class PunishmentsController {
	
	@Autowired
	private PunishmentsRepository punishmentsRepository;
	
	@GetMapping(path="/punishments/{reportId}")
	public @ResponseBody Punishments getReportsById(@PathVariable(value="reportId") int id){
    	return punishmentsRepository.findByreportsReportId(id);
    }
}

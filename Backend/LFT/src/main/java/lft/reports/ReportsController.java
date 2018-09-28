package lft.reports;
import org.springframework.web.bind.annotation.RestController;

import lft.demo.Profiles;
import lft.demo.UserRepository;

import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class ReportsController {
	
	@Autowired
    private ReportsRepository reportsRepository;
	
	@GetMapping(path="/all")
    public @ResponseBody Iterable<Reports> getAllreports(){
    	return reportsRepository.findAll();
    }
	
	@GetMapping(path="/all/{id}")
    public @ResponseBody Optional<Reports> getReportsById(@PathVariable(value="id") int id){
    	return reportsRepository.findById(id);
    }
	
	@GetMapping(path="/all/{prof_id}")
    public @ResponseBody Optional<Reports> getReportsByUserId(@PathVariable(value="prof_id") int prof_id){
    	return reportsRepository.findById(prof_id);
    }
	
	@GetMapping(path="/all/{resolveFlag}")
    public @ResponseBody Iterable<Reports> getUserByResolved(@PathVariable(value="resolveFlag") int resolveFlag){
    	return reportsRepository.findAllByResolveFlag(resolveFlag);
    }
}

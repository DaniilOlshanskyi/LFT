package lft.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class HelloController {
    
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Profiles> getAllUsers(){
    	return userRepository.findAll();
    }
} 

package lft.demo;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @GetMapping(path="/all/id/{id}")
    public @ResponseBody Optional<Profiles> getUserById(@PathVariable(value="id") int id){
    	return userRepository.findById(id);
    }
    
    @GetMapping(path="/all/username/{username}")
    public @ResponseBody Profiles getUserByUsername(@PathVariable(value="username") String username){
    	return userRepository.findByprofUsername(username);
    }
    
    @PostMapping("/post_profile")
    Profiles newProfile(@RequestBody Profiles profile) {
    	return userRepository.save(profile);
    }
    
    @GetMapping(path="/make_mod/{id}")
    public @ResponseBody String makeMod(@PathVariable(value="id") int id) {
    	Profiles user;
    	try {
    		 user = userRepository.findById(id).get();
    	} 
    	catch(NoSuchElementException e) {
    		return "No such user found!";
    	}
    	try {
    		user.setprofModFlag(1);
        	userRepository.save(user);
    	}
    	catch (Exception e) {
    		return e.toString();
    	}
    	return "User " + user.getprofUsername() + " is now a moderator!";
    }
} 

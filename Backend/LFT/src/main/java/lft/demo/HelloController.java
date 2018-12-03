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


/**
 * Spring controller to handle operations with user profiles.
 * 
 * @author Daniil Olshanskyi
 *
 */
@RestController
public class HelloController {
    
	/**
	 * Test mapping.
	 * 
	 * @return A test message.
	 */
    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Mapping to get all users.
     * 
     * @return all user profiles.
     */
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Profiles> getAllUsers(){
    	return userRepository.findAll();
    }
    
    /**
     * Mapping to fetch user by id.
     * 
     * @param id path parameter - the profile id.
     * @return profile object with this id or empty Optional object.
     */
    @GetMapping(path="/all/id/{id}")
    public @ResponseBody Optional<Profiles> getUserById(@PathVariable(value="id") int id){
    	return userRepository.findById(id);
    }
    
    /**
     * Mapping to fetch user by username.
     * 
     * @param username path parameter - the profile username.
     * @return profile object with the given username.
     */
    @GetMapping(path="/all/username/{username}")
    public @ResponseBody Profiles getUserByUsername(@PathVariable(value="username") String username){
    	return userRepository.findByprofUsername(username);
    }
    
    /**
     * Mapping to post a new profile object (create a new user).
     * 
     * @param profile profile object from the request body to be posted.
     * @return Profile object if saved successfully. 
     */
    @PostMapping("/post_profile")
    Profiles newProfile(@RequestBody Profiles profile) {
    	return userRepository.save(profile);
    }
    
    /**
     * Mapping to make user a moderator.
     * 
     * @param id id of profile to be made a moderator.
     * @return "No such user found!" if unsuccessful or "User {username} is now a moderator!" if successful.
     */
    @PutMapping(path="/make_mod/{id}")
    public @ResponseBody String makeMod(@PathVariable(value="id") int id) {
    	Profiles user;
    	try {
    		 user = userRepository.findById(id).get();
    		 System.out.println(user);
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
    
    /**
     * Mapping to make user not a moderator.
     * 
     * @param id id of profile to be made a non-mod.
     * @return "No such user found!" if unsuccessful or "User {username} is no longer a moderator!" if successful.
     */
    @PutMapping(path="/make_not_mod/{id}")
    public @ResponseBody String makeNotMod(@PathVariable(value="id") int id) {
    	Profiles user;
    	try {
    		 user = userRepository.findById(id).get();
    		 System.out.println(user);
    	} 
    	catch(NoSuchElementException e) {
    		return "No such user found!";
    	}
    	try {
    		user.setprofModFlag(0);
        	userRepository.save(user);
    	}
    	catch (Exception e) {
    		return e.toString();
    	}
    	return "User " + user.getprofUsername() + " is no longer a moderator!";
    }
} 

package lft.demo.games;

import org.springframework.web.bind.annotation.RestController;

import lft.demo.Profiles;
import lft.demo.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class GamesController {

	@Autowired
    private GamesRepository gamesRepository;
	
	@GetMapping(path="/games")
    public @ResponseBody Iterable<Games> getAllGames(){
    	return gamesRepository.findAll();
    }
}

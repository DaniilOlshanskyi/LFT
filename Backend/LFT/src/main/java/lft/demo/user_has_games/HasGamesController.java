package lft.demo.user_has_games;

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
public class HasGamesController {
	
	@Autowired
	private HasGamesRepository hasGamesRepository;
	
	@GetMapping(path="/hasGames/{userId}")
	public @ResponseBody List<HasGames> getHasGamesByProfile(@PathVariable(value="userId") int profId){
		return hasGamesRepository.findByprofID(profId);
	}
}

package lft.demo.games;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Spring controller to handle operations with games.
 * 
 * @author Daniil Olshanskyi
 *
 */
@RestController
public class GamesController {

	@Autowired
	private GamesRepository gamesRepository;

	/**
     * Mapping to get all games.
     * 
     * @return all games.
     */
	@GetMapping(path = "/games")
	public @ResponseBody Iterable<Games> getAllGames() {
		return gamesRepository.findAll();
	}

	/**
     * Mapping to post a new game object (create a new game).
     * 
     * @param game game object from the request body to be posted.
     * @return game object if saved successfully. 
     */
	@PostMapping("/post_game")
	public Games newGame(@RequestBody Games game) {
		return gamesRepository.save(game);
	}
	
	/**
	 * Mapping to delete a game.
	 * 
	 * @param id id of the game that needs to be deleted.
	 * @return "Success!" on success. 
	 */
	@DeleteMapping("/del_game/{id}")
	public String delGame(@PathVariable(value="id") int id) {
		gamesRepository.deleteById(id);
		return "Success!";
	}
}

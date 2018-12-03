package lft.demo.games;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository to hold games.
 * 
 * @author Daniil Olshanskyi
 *
 */
@Repository
public interface GamesRepository extends CrudRepository<Games, Integer> {
	Games findBygameName(String gameName);
}

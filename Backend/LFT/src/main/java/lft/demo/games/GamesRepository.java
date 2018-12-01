package lft.demo.games;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
@Component
public interface GamesRepository extends CrudRepository<Games, Integer> {
	Games findBygameName(String gameName);
}

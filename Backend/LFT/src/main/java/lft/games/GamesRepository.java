package lft.games;

import org.springframework.data.repository.CrudRepository;

public interface GamesRepository extends CrudRepository<Games, Integer> {
	Games findBygameName(String gameName);
}

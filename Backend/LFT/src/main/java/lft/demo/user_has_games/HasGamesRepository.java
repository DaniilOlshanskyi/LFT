package lft.demo.user_has_games;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface HasGamesRepository extends CrudRepository<HasGames, Integer> {
	List<HasGames> findBygameId(int gameId);
	
	List<HasGames> findByprofID(int profID);
}

package lft.demo.user_has_games;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface HasGamesRepository extends CrudRepository<HasGames, Integer> {
	List<HasGames> findBygameId(int gameId);
	
	List<HasGames> findByprofID(int profID);
}

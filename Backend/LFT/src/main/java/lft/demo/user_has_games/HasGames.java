package lft.demo.user_has_games;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/**
 * Class that represents the database entry of corresponding to hasGames object.
 * 
 * @author Daniil Olshanskyi
 *
 */
@Component
@Entity
@Table(name="profilesHasGames")
public class HasGames {
	
	@Column(name="profID")
    private @Id int profID;
	
	@Column(name="gameId")
    private int gameId;
	
	public int getprofID() {
		return profID;
	}
	public void setprofID(int profID) {
		this.profID = profID;
	}
	
	public int getgameId() {
		return gameId;
	}
	public void setgameId(int gameId) {
		this.gameId = gameId;
	}
	
}

package lft.demo.games;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
@Component
@Entity
@Table(name="games")
public class Games implements Serializable{
	
	@Column(name="gameId")
    private @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY ) Integer gameId;
	
	@Column(name="gameName")
    private String gameName;
	
	@Column(name="gameLogo")
    private String gameLogo;

	@Column(name="gameRanks")
    private String gameRanks;
	
	@Column(name="gamePlatform")
    private String gamePlatform;
	
	@Column(name="gameRoles")
    private String gameRoles;
	
	public Integer getId() {
		return gameId;
	}
	public void setId(Integer id) {
		this.gameId = id;
	}
	
	public String getgameName() {
		return gameName;
	}
	public void setgameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getgameLogo() {
		return gameLogo;
	}
	public void setgameLogo(String gameLogo) {
		this.gameLogo = gameLogo;
	}
	
	public String getgameRanks() {
		return gameRanks;
	}
	public void setgameRanks(String gameRanks) {
		this.gameRanks = gameRanks;
	}
	
	public String getgamePlatform() {
		return gamePlatform;
	}
	public void setgamePlatform(String gamePlatform) {
		this.gamePlatform = gamePlatform;
	}
	
	public String getgameRoles() {
		return gameRoles;
	}
	public void setgameRoles(String gameRoles) {
		this.gameRoles = gameRoles;
	}
}

package lft.demo.games;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
}

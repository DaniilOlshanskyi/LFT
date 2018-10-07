package lft.demo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="profiles")
public class Profiles implements Serializable{
	
		
		@Column(name="prof_id")
	    private @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY ) Integer prof_id;
		
		@Column(name="prof_username")
	    private String profusername;
		
		@Column(name="prof_password")
	    private String prof_password;
		
		@Column(name="prof_period")
	    private String prof_period;
		
		@Column(name="prof_gamesID")
	    private int prof_gamesID;
		
		@Column(name="prof_photo")
	    private String prof_photo;
		
		@Column(name="prof_reportFlag")
	    private int prof_reportFlag;
		
		@Column(name="prof_modFlag")
	    private int prof_modFlag;
		
		@Column(name="prof_rep")
	    private int prof_rep;
		
		@Column(name="prof_recentlogin")
	    private String prof_recentlogin;
		
		@Column(name="prof_suspend")
	    private int prof_suspend;
	    

		public Integer getId() {
			return prof_id;
		}
		public void setId(Integer id) {
			this.prof_id = id;
		}

		public String getprof_username() {
			return profusername;
		}
		public void setgetprof_username(String prof_username) {
			this.profusername = prof_username;
		}

		public String getprof_password() {
			return prof_password;
		}
		public void setprof_password(String prof_password) {
			this.prof_password = prof_password;
		}
		
		public String getprof_period() {
			return prof_period;
		}
		public void setprof_period(String prof_period) {
			this.prof_period = prof_period;
		}
		
		public int getprof_gamesID() {
			return prof_gamesID;
		}
		public void setprof_gamesID(int prof_gamesID) {
			this.prof_gamesID = prof_gamesID;
		}
		
		public String getprof_photo() {
			return prof_photo;
		}
		public void setprof_photo(String prof_photo) {
			this.prof_photo = prof_photo;
		}
		
		public int getprof_reportFlag() {
			return prof_reportFlag;
		}
		public void setprof_reportFlag(int prof_reportFlag) {
			this.prof_reportFlag = prof_reportFlag;
		}
		
		public int getprof_modFlag() {
			return prof_modFlag;
		}
		public void setprof_modFlag(int prof_modFlag) {
			this.prof_modFlag = prof_modFlag;
		}
		
		public int getprof_rep() {
			return prof_rep;
		}
		public void setprof_rep(int prof_rep) {
			this.prof_rep = prof_rep;
		}
		
		public String getprof_recentlogin() {
			return prof_recentlogin;
		}
		public void setprof_recentlogin(String prof_recentlogin) {
			this.prof_recentlogin = prof_recentlogin;
		}
		
		public int getprof_suspend() {
			return prof_suspend;
		}
		public void setprof_suspend(int prof_suspend) {
			this.prof_suspend = prof_suspend;
		}
}

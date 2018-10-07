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
	
		
		@Column(name="profId")
	    private @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY ) Integer profId;
		
		@Column(name="profUsername")
	    private String profUsername;
		
		@Column(name="profPassword")
	    private String profPassword;
		
		@Column(name="profPeriod")
	    private String profPeriod;
		
		@Column(name="profGamesID")
	    private int profGamesID;
		
		@Column(name="profPhoto")
	    private String profPhoto;
		
		@Column(name="profReportFlag")
	    private int profReportFlag;
		
		@Column(name="profModFlag")
	    private int profModFlag;
		
		@Column(name="profRep")
	    private int profRep;
		
		@Column(name="profRecentlogin")
	    private String profRecentlogin;
		
		@Column(name="profSuspend")
	    private int profSuspend;
	    

		public Integer getId() {
			return profId;
		}
		public void setId(Integer id) {
			this.profId = id;
		}

		public String getprofUsername() {
			return profUsername;
		}
		public void setgetprofusername(String profUsername) {
			this.profUsername = profUsername;
		}

		public String getprofPassword() {
			return profPassword;
		}
		public void setprofPassword(String profPassword) {
			this.profPassword = profPassword;
		}
		
		public String getprofPeriod() {
			return profPeriod;
		}
		public void setprofPeriod(String profPeriod) {
			this.profPeriod = profPeriod;
		}
		
		public int getprofGamesID() {
			return profGamesID;
		}
		public void setprofGamesID(int profGamesID) {
			this.profGamesID = profGamesID;
		}
		
		public String getprofPhoto() {
			return profPhoto;
		}
		public void setprof_photo(String profPhoto) {
			this.profPhoto = profPhoto;
		}
		
		public int getprofReportFlag() {
			return profReportFlag;
		}
		public void setprofReportFlag(int profReportFlag) {
			this.profReportFlag = profReportFlag;
		}
		
		public int getprofModFlag() {
			return profModFlag;
		}
		public void setprofModFlag(int profModFlag) {
			this.profModFlag = profModFlag;
		}
		
		public int getprofRep() {
			return profRep;
		}
		public void setprofRep(int profRep) {
			this.profRep = profRep;
		}
		
		public String getprofRecentlogin() {
			return profRecentlogin;
		}
		public void setprofRecentlogin(String profRecentlogin) {
			this.profRecentlogin = profRecentlogin;
		}
		
		public int getprofSuspend() {
			return profSuspend;
		}
		public void setprofSuspend(int profSuspend) {
			this.profSuspend = profSuspend;
		}
}

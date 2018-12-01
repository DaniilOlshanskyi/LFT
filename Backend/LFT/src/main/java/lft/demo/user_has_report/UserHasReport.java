package lft.demo.user_has_report;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lft.demo.Profiles;
import lft.demo.reports.Reports;
@Component
@Entity
@Table(name="profilesHasReports")
public class UserHasReport implements Serializable{
	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="profId")
	@Column(name = "profiles_profID")
	private @Id int profiles_profID;
	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="reportId")
	@Column(name = "profiles_reportId")
	private int profiles_reportId;
	
//	public UserHasReport(int profId, int reportId) {
//		this.profiles_profID = profId;
//		this.profiles_reportId = reportId;
//	}
//	
	public int getprofId() {
		return profiles_profID;
	}
	
	public int getreportId() {
		return profiles_reportId;
	}
	
	public void setprofId(int profId) {
		this.profiles_profID=profId;
	}
	
	public void setreportId(int reportId) {
		this.profiles_reportId=reportId;
	}
}

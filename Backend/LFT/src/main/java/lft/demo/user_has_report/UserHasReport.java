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

import lft.demo.Profiles;
import lft.demo.reports.Reports;

@Entity
@Table(name="profilesHasReports")
public class UserHasReport implements Serializable{
	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="profId")
	private int profId;
	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinColumn(name="reportId")
	private int reportId;
	
	public UserHasReport(int profId, int reportId) {
		this.profId = profId;
		this.reportId = reportId;
	}
	
	public int getProfId() {
		return profId;
	}
	
	public int getReportId() {
		return reportId;
	}
}

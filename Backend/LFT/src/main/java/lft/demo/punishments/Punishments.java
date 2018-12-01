package lft.demo.punishments;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="punishment")
public class Punishments {
	
	@Column(name="date")
	private Date date;
	
	@Column(name="redemptionChance")
	private int redemptionChance;
	
	@Column(name="reportsReportId")
	private int reportId;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date newDate) {
		date = newDate;
	}
	
	public int getRedemptionChance() {
		return redemptionChance;
	}
	public void setRedemtionChance(int chance) {
		redemptionChance = chance;
	}
	
	public int getReportsReportId() {
		return reportId;
	}
	public void setReportsReportId(int id) {
		reportId = id;
	}

}

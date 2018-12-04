package lft.demo.reports;

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
/**
 * Class that represents the database entry of corresponding to a report.
 * 
 * @author Mike Ostrow
 *
 */
@Component
@Entity
@Table(name="reports")
public class Reports implements Serializable{
	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name="profId")
//	private Profiles profId;

	@Column(name="reportId")
	private @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY ) Integer reportID;
	
//	@Column(name="profId")
//	private Integer profId;
	
	@Column(name="reportChatlog")
	private Long reportChatlog;
	
	@Column(name="reportResolveFlag")
	private Integer reportResolveFlag;
	
	@Column(name="reportResolveDate")
	private String reportResolveDate;
	
	@Column(name="reportMessage")
	private String reportMessage;
	
	public Integer getId() {
		return reportID;
	}
	public void setId(Integer id) {
		reportID = id;
	}
	
//	public Profiles getProfId() {
//		return profId;
//	}
//	public void setProfId(Profiles profId) {
//		this.profId = profId;
//	}
//	public void setProfId(Integer id) {
//		profId = id;
//	}
	
	public Long getreportChatlog() {
		return reportChatlog;
	}
	public void setreportChatlog(Long chatlog) {
		reportChatlog = chatlog;
	}
	
	public Integer getreportResolveFlag() {
		return reportResolveFlag;
	}
	public void setreportResolveFlag(Integer resolveFlag) {
		reportResolveFlag = resolveFlag;
	}
	
	public String getreportResolveDate() {
		return reportResolveDate;
	}
	public void setreportResolveDate(String resolveDate) {
		reportResolveDate = resolveDate;
	}
	
	public String getreportMessage() {
		return reportMessage;
	}
	public void setreportMessage(String message) {
		reportMessage = message;
	}
}

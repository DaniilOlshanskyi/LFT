package lft.demo.reports;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reports")
public class Reports implements Serializable{

	@Column(name="reportId")
	private @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY ) Integer reportID;
	
	@Column(name="profId")
	private Integer profId;
	
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
	
	public Integer getProfId() {
		return profId;
	}
	public void setProfId(Integer id) {
		profId = id;
	}
	
	public Long getChatlog() {
		return reportChatlog;
	}
	public void setChatlog(Long chatlog) {
		reportChatlog = chatlog;
	}
	
	public Integer getResolveFlag() {
		return reportResolveFlag;
	}
	public void setResolveFlag(Integer resolveFlag) {
		reportResolveFlag = resolveFlag;
	}
	
	public String getResolveDate() {
		return reportResolveDate;
	}
	public void setResolveDate(String resolveDate) {
		reportResolveDate = resolveDate;
	}
	
	public String getMessage() {
		return reportMessage;
	}
	public void setMessage(String message) {
		reportMessage = message;
	}
}
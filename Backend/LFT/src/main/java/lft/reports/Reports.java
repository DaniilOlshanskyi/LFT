package lft.reports;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="reports")
public class Reports implements Serializable{

	@Id
	@Column(name="report_id")
	private Integer report_id;
	
	@Column(name="prof_id")
	private Integer prof_id;
	
	@Column(name="report_chatlog")
	private Long report_chatlog;
	
	@Column(name="report_resolveFlag")
	private Integer report_resolveFlag;
	
	@Column(name="report_resolveDate")
	private String report_resolveDate;
	
	@Column(name="report_message")
	private String report_message;
	
	public Integer getId() {
		return report_id;
	}
	public void setId(Integer id) {
		report_id = id;
	}
	
	public Integer getProf_id() {
		return prof_id;
	}
	public void setProf_id(Integer id) {
		prof_id = id;
	}
	
	public Long getChatlog() {
		return report_chatlog;
	}
	public void setChatlog(Long chatlog) {
		report_chatlog = chatlog;
	}
	
	public Integer getResolveFlag() {
		return report_resolveFlag;
	}
	public void setResolveFlag(Integer resolveFlag) {
		report_resolveFlag = resolveFlag;
	}
	
	public String getResolveDate() {
		return report_resolveDate;
	}
	public void setResolveDate(String resolveDate) {
		report_resolveDate = resolveDate;
	}
	
	public String getMessage() {
		return report_message;
	}
	public void setMessage(String message) {
		report_message = message;
	}
}

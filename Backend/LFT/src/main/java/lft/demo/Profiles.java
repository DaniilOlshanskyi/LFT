package lft.demo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profiles implements Serializable {

	@Column(name = "profID")
	private @Id @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY) Integer profID;

	@Column(name = "profUsername")
	private String profUsername;

	@Column(name = "profPassword")
	private String profPassword;

	@Column(name = "profPeriod")
	private String profPeriod;

	@Column(name = "profPhoto")
	private String profPhoto;

	@Column(name = "profReportFlag")
	private int profReportFlag;

	@Column(name = "profModFlag")
	private int profModFlag;

	@Column(name = "profRep")
	private float profRep;

	@Column(name = "profRecentlogin")
	private String profRecentlogin;

	@Column(name = "profSuspend")
	private float profSuspend;

	public Integer getID() {
		return profID;
	}

	public void setID(Integer id) {
		this.profID = id;
	}

	public String getprofUsername() {
		return profUsername;
	}

	public void setprofusername(String profUsername) {
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

	public float getprofRep() {
		return profRep;
	}

	public void setprofRep(float profRep) {
		this.profRep = profRep;
	}

	public String getprofRecentlogin() {
		return profRecentlogin;
	}

	public void setprofRecentlogin(String profRecentlogin) {
		this.profRecentlogin = profRecentlogin;
	}

	public float getprofSuspend() {
		return profSuspend;
	}

	public void setprofSuspend(float profSuspend) {
		this.profSuspend = profSuspend;
	}

	public String toString() {
		return profID + "&" + profUsername + "&" + profPassword + "&" + profPeriod + "&" + profPhoto + "&"
				+ profReportFlag + "&" + profModFlag + "&" + profRep + "&" + profRecentlogin + "&" + profSuspend;
	}
}

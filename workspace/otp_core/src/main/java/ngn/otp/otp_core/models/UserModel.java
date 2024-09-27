package ngn.otp.otp_core.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @Column(name = "userid", nullable = false)
    private String userId;

    @Column(name = "phone1",unique=true, nullable = true)
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "organization")
    private String organization;

    @Column(name = "code")
    private String code;

    @Column(name = "privatekey",nullable = false)
    @JsonIgnore
    private String privateKey;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "manualcode")
    private String manualCode;

    @Column(name = "datecreated")
    private Date dateCreated;

    @Column(name = "datemodified")
    private Date dateModified;

    @Column(name = "isadmin")
    private Boolean isAdmin=false;

    @Lob
    @Column(name = "password")
    @JsonIgnore
    private byte[] password;

    @Column(name = "enablesms")
    private Boolean enableSms=false;

    @Column(name = "enableappcode")
    private Boolean enableAppCode=false;

    @Column(name = "email",unique=true,nullable = true)
    private String email;

    @Column(name = "jobtitle")
    private String jobTitle;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "enableotpapp")
    private Boolean enableOtpApp=false;

    @Column(name = "required")
    private Boolean required=false;

    @Column(name = "lastlogindate")
    private Long lastLoginDate;

    @Column(name = "logonduration")
    private Integer logonDuration;
    
    @Column(name = "activecode",unique=true)
    private String activeCode;
    
    @Column(name="pincode")
    private String pinCode;
    
    @Column(name="searchfield")
    private String searchField;

	public UserModel(String userId, String phone1, String phone2, String organization, String code, String privateKey,
			Boolean enable, String manualCode, Date dateCreated, Date dateModified, Boolean isAdmin, byte[] password,
			Boolean enableSms, Boolean enableAppCode, String email, String jobTitle, String cccd, String fullName,
			Boolean enableOtpApp, Boolean required, Long lastLoginDate, Integer logonDuration, String activeCode) {
		super();
		this.userId = userId;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.organization = organization;
		this.code = code;
		this.privateKey = privateKey;
		this.enable = enable;
		this.manualCode = manualCode;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
		this.isAdmin = isAdmin;
		this.password = password;
		this.enableSms = enableSms;
		this.enableAppCode = enableAppCode;
		this.email = email;
		this.jobTitle = jobTitle;
		this.cccd = cccd;
		this.fullName = fullName;
		this.enableOtpApp = enableOtpApp;
		this.required = required;
		this.lastLoginDate = lastLoginDate;
		this.logonDuration = logonDuration;
		this.activeCode = activeCode;
	}
    
    public UserModel() {
    	
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getManualCode() {
		return manualCode;
	}

	public void setManualCode(String manualCode) {
		this.manualCode = manualCode;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public Boolean getEnableSms() {
		return enableSms;
	}

	public void setEnableSms(Boolean enableSms) {
		this.enableSms = enableSms;
	}

	public Boolean getEnableAppCode() {
		return enableAppCode;
	}

	public void setEnableAppCode(Boolean enableAppCode) {
		this.enableAppCode = enableAppCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getEnableOtpApp() {
		return enableOtpApp;
	}

	public void setEnableOtpApp(Boolean enableOtpApp) {
		this.enableOtpApp = enableOtpApp;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Long getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Long lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getLogonDuration() {
		return logonDuration;
	}

	public void setLogonDuration(Integer logonDuration) {
		this.logonDuration = logonDuration;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
	
    
    
    
    
}


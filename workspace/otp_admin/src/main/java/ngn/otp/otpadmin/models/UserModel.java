package ngn.otp.otpadmin.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


public class UserModel {
    
    private String userId;

  
    private String phone1;

  
    private String phone2;

   
    private String organization;

   
    private String code;

   
    @JsonIgnore
    private String privateKey;

   
    private Boolean enable;

    
    private String manualCode;

   
    private Date dateCreated;

   
    private Date dateModified;

    private Boolean isAdmin=false;


    private byte[] password;

    @Column(name = "enablesms")
    private Boolean enableSms=false;

   
    private Boolean enableAppCode=false;

   
    private String email;

   
    private String jobTitle;

    
    private String cccd;

  
    private String fullName;

    
    private Boolean enableOtpApp=false;

    
    private Boolean required=false;


    private Long lastLoginDate;

   
    private Integer logonDuration;
    
    
    private String activeCode;
    
  
    private String pinCode;
    
   
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


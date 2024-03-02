package ngn.otp.otp_core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "userid", nullable = false)
    private String userId;

    @Column(name = "phone1",unique=true)
    private String phone1;

    @Column(name = "phone2")
    private String phone2;

    @Column(name = "organization")
    private String organization;

    @Column(name = "code")
    private String code;

    @Column(name = "privatekey")
    private String privateKey;

    @Column(name = "enable")
    private Boolean enable;

    @Column(name = "manualcode")
    private String manualCode;

    @Column(name = "datecreated")
    private Long dateCreated;

    @Column(name = "datemodified")
    private Long dateModified;

    @Column(name = "isadmin")
    private Boolean isAdmin=false;

    @Lob
    @Column(name = "password")
    private byte[] password;

    @Column(name = "enablesms")
    private Boolean enableSms=false;

    @Column(name = "enableappcode")
    private Boolean enableAppCode=false;

    @Column(name = "email",unique=true)
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

	public Long getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Long dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Long getDateModified() {
		return dateModified;
	}

	public void setDateModified(Long dateModified) {
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
    

    // Constructors, getters, and setters
    
    
}


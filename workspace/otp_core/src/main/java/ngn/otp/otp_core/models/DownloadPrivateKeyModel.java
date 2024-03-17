package ngn.otp.otp_core.models;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Table(name = "download_privatekey")
public class DownloadPrivateKeyModel {

	@Id
	@Column(name = "userid", length = 100)
	private String userId;

	@Column(name = "deviceid", length = 45)
	private String deviceId;

	@Column(name = "devicename", length = 45)
	private String deviceName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createddate")
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifieddate")
	private Date modifiedDate;

	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
	private UserModel userModel;


	// Constructors, getters, and setters

	public DownloadPrivateKeyModel() {

	}

	public DownloadPrivateKeyModel(UserModel userModel, String deviceId, String deviceName
			 ) {
		super();
		this.userId = userModel.getUserId();
		this.deviceId = deviceId;
		this.deviceName = deviceName;
		this.userModel = userModel;
		this.createdDate = new Date();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public UserModel getUserModel() {
		return userModel;
	}

	public void setUserModel(UserModel userModel) {
		this.userModel = userModel;
	}







}

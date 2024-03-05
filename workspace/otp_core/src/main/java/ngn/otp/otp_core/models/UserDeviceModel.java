package ngn.otp.otp_core.models;
import java.util.Date;

import jakarta.persistence.*;
@Entity
@Table(name = "user_devices")
public class UserDeviceModel {

    @EmbeddedId
    private UserDeviceId id;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private UserModel user;

    @Column(name = "devicename", length = 100, nullable = false)
    private String deviceName;

    @Column(name = "createdate")
    private Date createDate;
    
    // Constructors, getters, and setters
    public UserDeviceModel() {
    	
    }

	public UserDeviceModel(UserDeviceId id, UserModel user, String deviceName, Date createDate) {
		super();
		this.id = id;
		this.user = user;
		this.deviceName = deviceName;
		this.createDate = createDate;
	}

	public UserDeviceId getId() {
		return id;
	}

	public void setId(UserDeviceId id) {
		this.id = id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	

  
    
}

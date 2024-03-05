package ngn.otp.otp_core.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserDeviceId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "userid", length = 100)
	private String userId;

	@Column(name = "code", length = 16)
	private String code;
	
	// Constructors, getters, and setters
	public UserDeviceId(String userId, String code) {
		super();
		this.userId = userId;
		this.code = code;
	}
	
	public UserDeviceId() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

}

package ngn.otp.otp_core.models;

import jakarta.persistence.Embeddable;


@Embeddable
public class UserApplicationId {

	private String userId;
    private String applicationId;

    // Constructors, getters, and setters
    public UserApplicationId() {
    	
    }
	public UserApplicationId(String userId, String applicationId) {
		super();
		this.userId = userId;
		this.applicationId = applicationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

    
}


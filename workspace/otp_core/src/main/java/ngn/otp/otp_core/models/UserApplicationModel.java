package ngn.otp.otp_core.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_application")
public class UserApplicationModel {

    @EmbeddedId
    @Column(name = "applicationid")
    private UserApplicationId id;

    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userid")
    private UserModel user;

    @ManyToOne
    @MapsId("applicationId")
    @JoinColumn(name = "applicationid")
    private ApplicationModel applicationModel;
    
    // Constructors, getters, and setters
    public UserApplicationModel() {
    	
    }
	public UserApplicationModel(UserApplicationId id, UserModel user, ApplicationModel application) {
		super();
		this.id = id;
		this.user = user;
		this.applicationModel = application;
	}
	
	public UserApplicationId getId() {
		return id;
	}
	
	public void setId(UserApplicationId id) {
		this.id = id;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public ApplicationModel getApplication() {
		return applicationModel;
	}

	public void setApplication(ApplicationModel application) {
		this.applicationModel = application;
	}
}


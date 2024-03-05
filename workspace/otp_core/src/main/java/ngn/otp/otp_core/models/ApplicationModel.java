package ngn.otp.otp_core.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "application")
public class ApplicationModel {

    @Id
    @Column(name = "applicationid")
    private String applicationId;

    @Column(name = "applicationname",unique=true)
    private String applicationName;

    @Column(name = "description")
    private String description;

    // Constructors, getters, and setters
    public ApplicationModel() {
    }

    public ApplicationModel(String applicationId, String applicationName, String description) {
    	this.applicationId=applicationId;
    	this.applicationName=applicationName;
    	this.description=description;
    
    }

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    

    
}

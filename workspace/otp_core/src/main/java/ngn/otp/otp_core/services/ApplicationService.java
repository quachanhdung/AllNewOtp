package ngn.otp.otp_core.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.models.ApplicationModel;
import ngn.otp.otp_core.repositories.ApplicationRepo;

@Component
public class ApplicationService {
	private ApplicationRepo applicationRep = null;
	public static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
	public ApplicationService(ApplicationRepo applicationRep) {
		this.applicationRep = applicationRep;
	}
	
	public ApplicationModel findByApplicationName(String applicationName) {
		return applicationRep.findByApplicationName(applicationName).orElse(null);
	}
	
	public ApplicationModel add(ApplicationModel model) {
		return applicationRep.save(model);
	}

	public List<ApplicationModel> getAll() {
	
		return applicationRep.findAll();
	}

	public ApplicationModel findById(String applicationId) {
		return applicationRep.findById(applicationId).orElse(null);
	}

	public void save(ApplicationModel model) {
		applicationRep.save(model);
		
	}

	public void delete(ApplicationModel model) {
		applicationRep.delete(model);
		
	}

}

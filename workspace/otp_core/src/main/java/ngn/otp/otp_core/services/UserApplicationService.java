package ngn.otp.otp_core.services;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.models.ApplicationModel;
import ngn.otp.otp_core.models.UserApplicationModel;
import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.repositories.UserApplicationRepo;

@Component
public class UserApplicationService {
	private UserApplicationRepo userApplicationRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(UserApplicationService.class);
	public UserApplicationService(UserApplicationRepo userApplicationRepo) {
		this.userApplicationRepo = userApplicationRepo;
	}
	public List<UserApplicationModel> getAll() {
		return userApplicationRepo.findAll();
	}
	public void save(UserApplicationModel model) {
		userApplicationRepo.save(model);
	}

	public void deleteByUser(UserModel userModel) {
		userApplicationRepo.deleteByUser(userModel);
		
	}
	public void delelteByApplicationModel(ApplicationModel model) {
		userApplicationRepo.deleteByApplicationModel(model);
		
	}
	public List<UserApplicationModel> findByUser(UserModel userModel) {
		
		return userApplicationRepo.findByUser(userModel);
	}
	
	public List<UserApplicationModel> findByApplicationModel(ApplicationModel applicationModel){
		return userApplicationRepo.findByApplicationModel(applicationModel);
	}
	public UserApplicationModel findByUserAndApplicationModel(UserModel userModel, ApplicationModel applicationModel) {
		return userApplicationRepo.findByUserAndApplicationModel(userModel,applicationModel).orElse(null);
	}
	

}
package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.repositories.UserRepo;

@Component
public class UserService {
	private UserRepo userRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	public UserService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	public UserModel findById(String userId) {
		return userRepo.findById(userId).orElse(null);
	}
	public void save(UserModel userModel) {
		userRepo.save(userModel);
	}

}
package ngn.otp.otp_core.services;

import java.util.List;

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
		String searchField = 
				userModel.getUserId()+" "
				+userModel.getEmail()+" "
				+userModel.getPhone1()
				+" "+userModel.getFullName()
				+" "+userModel.getOrganization();
		
		userModel.setSearchField(searchField);
		userRepo.save(userModel);
		
	}
	public void delete(UserModel model) {
		userRepo.delete(model);
		
	}
	public UserModel findByActiveCode(String activeCode) {
		return userRepo.findByActiveCode(activeCode).orElse(null);
	}
	public UserModel findByPhone(String phone) {
		
		return userRepo.findByPhone1(phone).orElse(null);
	}
	public UserModel findByEmail(String email) {
		
		return userRepo.findByEmail(email).orElse(null);
	}
	public List<UserModel> getAll() {
		
		return userRepo.findAll();
	}
	public Object search(String keyword, int offset, int limit) {
		logger.info("UserService::search()");
		logger.info(keyword+" "+offset+" "+limit);
		return userRepo.search(keyword, offset, limit);
	}
	public long searchCount(String keyword) {
		keyword = "%"+keyword+"%";
		return userRepo.countBySearchFieldLike(keyword);
	}

}

package ngn.otp.otp_core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import ngn.otp.otp_core.models.ApplicationModel;
import ngn.otp.otp_core.models.UserApplicationId;
import ngn.otp.otp_core.models.UserApplicationModel;
import ngn.otp.otp_core.models.UserModel;


public interface UserApplicationRepo extends JpaRepository<UserApplicationModel, UserApplicationId>{

	@Transactional
	void deleteByUser(UserModel userModel);
	
	@Transactional
	void deleteByApplicationModel(ApplicationModel applicationModel);

	List<UserApplicationModel> findByUser(UserModel userModel);

	List<UserApplicationModel> findByApplicationModel(ApplicationModel applicationModel);

}

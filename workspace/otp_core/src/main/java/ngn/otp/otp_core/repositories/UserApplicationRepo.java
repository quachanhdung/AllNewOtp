package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.UserApplicationId;
import ngn.otp.otp_core.models.UserApplicationModel;


public interface UserApplicationRepo extends JpaRepository<UserApplicationModel, UserApplicationId>{

}

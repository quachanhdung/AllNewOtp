package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.UserModel;

public interface UserRepo extends JpaRepository<UserModel, String>{

}

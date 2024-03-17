package ngn.otp.otp_core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.UserModel;

public interface UserRepo extends JpaRepository<UserModel, String>{

	Optional<UserModel> findByActiveCode(String activeCode);

	Optional<UserModel> findByPhone1(String phone);

	Optional<UserModel> findByEmail(String email);

}

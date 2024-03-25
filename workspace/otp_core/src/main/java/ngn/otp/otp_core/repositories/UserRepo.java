package ngn.otp.otp_core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ngn.otp.otp_core.models.UserModel;

public interface UserRepo extends JpaRepository<UserModel, String>{

	Optional<UserModel> findByActiveCode(String activeCode);

	Optional<UserModel> findByPhone1(String phone);

	Optional<UserModel> findByEmail(String email);

	@Query(value = "select * from user where searchfield like concat('%',?,'%') limit ?,?", nativeQuery = true)
	List<UserModel> search(String keyword, int offset, int limit);
	
	long countBySearchFieldLike(String keyword);

}

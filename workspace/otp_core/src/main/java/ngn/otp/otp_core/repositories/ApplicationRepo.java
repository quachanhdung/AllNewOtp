package ngn.otp.otp_core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.ApplicationModel;

public interface ApplicationRepo extends JpaRepository<ApplicationModel, String>{

	Optional<ApplicationModel> findByApplicationName(String applicationName);


}

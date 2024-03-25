package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ngn.otp.otp_core.models.DownloadPrivateKeyModel;

public interface DownloadPrivateKeyRepo extends JpaRepository<DownloadPrivateKeyModel, String>{

	@Transactional
	void deleteByUserId(String userId);

}
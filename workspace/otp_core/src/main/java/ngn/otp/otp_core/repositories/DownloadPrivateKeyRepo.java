package ngn.otp.otp_core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ngn.otp.otp_core.models.DownloadPrivateKeyModel;

public interface DownloadPrivateKeyRepo extends JpaRepository<DownloadPrivateKeyModel, String>{

}
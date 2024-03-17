package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.models.DownloadPrivateKeyModel;
import ngn.otp.otp_core.repositories.DownloadPrivateKeyRepo;
@Component
public class DownloadPrivateKeyService {
	private DownloadPrivateKeyRepo downloadPrivateKeyRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(DownloadPrivateKeyService.class);
	public DownloadPrivateKeyService(DownloadPrivateKeyRepo downloadPrivateKeyRepo) {
		this.downloadPrivateKeyRepo = downloadPrivateKeyRepo;
	}
	public void save(DownloadPrivateKeyModel model) {
		downloadPrivateKeyRepo.save(model);
		
	}
	public void deleteByUserId(String userId) {
		downloadPrivateKeyRepo.deleteByUserId(userId);
		
	}
}

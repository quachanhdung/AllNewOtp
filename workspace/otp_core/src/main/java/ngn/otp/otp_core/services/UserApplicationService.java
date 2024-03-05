package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.repositories.UserApplicationRepo;

@Component
public class UserApplicationService {
	private UserApplicationRepo userApplicationRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(UserApplicationService.class);
	public UserApplicationService(UserApplicationRepo userApplicationRepo) {
		this.userApplicationRepo = userApplicationRepo;
	}

}
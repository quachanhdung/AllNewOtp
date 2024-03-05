package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.repositories.UserDeviceRepo;

@Component
public class UserDeviceService {
	private UserDeviceRepo userDeviceRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(UserDeviceService.class);
	public UserDeviceService(UserDeviceRepo userDeviceRepo) {
		this.userDeviceRepo = userDeviceRepo;
	}

}
package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.repositories.ZimbraPreAuthKeyRepo;

@Component
public class ZimbraPreAuthKeyService {
	private ZimbraPreAuthKeyRepo zimbraPreAuthKeyRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(ZimbraPreAuthKeyService.class);
	public ZimbraPreAuthKeyService(ZimbraPreAuthKeyRepo zimbraPreAuthKeyRepo) {
		this.zimbraPreAuthKeyRepo = zimbraPreAuthKeyRepo;
	}

}

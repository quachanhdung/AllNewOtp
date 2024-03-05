package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.repositories.ZimbraLdapKeyRepo;

@Component
public class ZimbraLdapKeyService {
	private ZimbraLdapKeyRepo zimbraLdapKeyRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(ZimbraLdapKeyService.class);
	public ZimbraLdapKeyService(ZimbraLdapKeyRepo zimbraLdapKeyRepo) {
		this.zimbraLdapKeyRepo = zimbraLdapKeyRepo;
	}

}
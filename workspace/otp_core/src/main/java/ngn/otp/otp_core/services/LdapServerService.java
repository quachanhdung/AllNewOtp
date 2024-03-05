package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.repositories.LdapServerRepo;

@Component
public class LdapServerService {
	
	private LdapServerRepo ldapServerRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(LdapServerService.class);
	public LdapServerService(LdapServerRepo ldapServerRepo) {
		this.ldapServerRepo = ldapServerRepo;
	}

}

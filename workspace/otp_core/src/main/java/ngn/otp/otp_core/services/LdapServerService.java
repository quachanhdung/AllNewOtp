package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.models.LdapServerModel;
import ngn.otp.otp_core.repositories.LdapServerRepo;

@Component
public class LdapServerService {
	
	private LdapServerRepo ldapServerRepo = null;
	public static final Logger logger = LoggerFactory.getLogger(LdapServerService.class);
	public LdapServerService(LdapServerRepo ldapServerRepo) {
		this.ldapServerRepo = ldapServerRepo;
	}
	public Object getAll() {
		return ldapServerRepo.findAll();
	}
	public LdapServerModel save(LdapServerModel model) {
		return ldapServerRepo.save(model);
		
	}
	public LdapServerModel findById(Long serverId) {
		return ldapServerRepo.findById(serverId).orElse(null);
	}
	public void delete(LdapServerModel model) {
		ldapServerRepo.delete(model);
		
	}

}

package ngn.otp.otp_core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.repositories.ApplicationRepo;

@Component
public class ApplicationService {
	private ApplicationRepo applicationRep = null;
	public static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
	public ApplicationService(ApplicationRepo applicationRep) {
		this.applicationRep = applicationRep;
	}

}

package ngn.otp.otp_core.services;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ngn.otp.otp_core.models.LdapServerModel;
import ngn.otp.otp_core.repositories.LdapServerRepo;
import ngn.otp.otp_core.utils.ActiveDirectory;
import ngn.otp.otp_core.utils.LdapUtil;

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
	
	public List<LdapServerModel> findAll(){
		return ldapServerRepo.findByOrderByDefaultServerDesc();
	}
	
	
	public List<LdapServerModel> findByDefault() {
		return ldapServerRepo.findByDefaultServerTrue();	
	}
	
	public Map<String,Object> authen(String userId, String password) {
		List<LdapServerModel> listServer =findAll();
		if(listServer.isEmpty()) return null;
		Map<String, Object> result=null;
		for(LdapServerModel model : listServer) {
			logger.info("server url: "+model.getServerUrl());

			result= LdapUtil.authByLdap(model.getServerUrl(),userId,password,model.getUserDn(),model.getSearchBase(),model.getQueryFilter(),model.getResultAttributes());
			if(result!=null) return result;
		}
		return result;
		
	}
	
//	public boolean ldapAuthenticate(LdapServerModel model) {
//		logger.info("=====LdapServerService::ldapAuthenticate()=====");
//		String userName = model.getPrincipal();
//		String password = model.getCredential();
//		userName=userName.split("@")[0];
//		if (userName.contains("\\")){
//			userName = userName.substring(userName.indexOf("\\")+1);
//		}
//		
//		String PROVIDER_URL = model.getServerUrl();
//	    String SECURITY_AUTHENTICATION = "simple";
//	    String SECURITY_PRINCIPAL = model.getBinddn()+"=" + userName + "," + model.getSearchBase();
//	    String SECURITY_CREDENTIALS = password;
//	    
//	    logger.info("PROVIDER_URL: "+PROVIDER_URL);
//	    logger.info("SECURITY_AUTHENTICATION: "+SECURITY_AUTHENTICATION);
//	    logger.info("SECURITY_PRINCIPAL: "+SECURITY_PRINCIPAL);
//	    logger.info("SECURITY_CREDENTIALS: "+SECURITY_CREDENTIALS);
//		
//		Hashtable<String, String> env = new Hashtable<String, String>();
//	    env.put("com.sun.jndi.ldap.connect.timeout", "2000");
//	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//	    env.put(Context.PROVIDER_URL, PROVIDER_URL);
//	    env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
//	    env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
//	    env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
//	    DirContext ctx = null;
//	    boolean returnValue=false;
//	    try {
//	        ctx = new InitialDirContext(env);
//	        returnValue =true ;
//	    } catch (NamingException e) {
//	    	logger.error(e.toString());
//
//	    } finally {
//	        if (ctx != null) {
//	            try { ctx.close(); } catch (NamingException e) {}
//	        }
//	    }
//	    return returnValue;
//	}
//	
//	public static boolean adAuthenticate(LdapServerModel model) {
//		logger.info("=====LdapServerService::adAuthenticate()=====");
//		String userName = model.getPrincipal().trim();
//		String password = model.getCredential().trim();
//		String domainName=model.getDomainName();
//		userName = userName.split("@")[0];
//		if (userName.contains("\\")){
//			userName = userName.substring(userName.indexOf("\\")+1);
//		}
//		
//		
//	    String PROVIDER_URL = model.getServerUrl();
//	    String SECURITY_AUTHENTICATION = "simple";
//	    String SECURITY_PRINCIPAL = userName + "@" + domainName;
//	    String SECURITY_CREDENTIALS = password;
//	   
//	  
//	   
//	    logger.info("PROVIDER_URL: "+PROVIDER_URL);
//		logger.info("SECURITY_AUTHENTICATION: "+SECURITY_AUTHENTICATION);
//		logger.info("SECURITY_PRINCIPAL: "+SECURITY_PRINCIPAL);
//		logger.info("SECURITY_CREDENTIALS: "+SECURITY_CREDENTIALS);
//		
//	    
//	    Hashtable<String, String> env = new Hashtable<String, String>();
//	    env.put("com.sun.jndi.ldap.connect.timeout", "2000");
//	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//	    env.put(Context.PROVIDER_URL, PROVIDER_URL);
//	    env.put(Context.SECURITY_AUTHENTICATION, SECURITY_AUTHENTICATION);
//	    env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL);
//	    env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS);
//	    DirContext ctx = null;
//	    try {
//	        ctx = new InitialDirContext(env);
//	        return true; // authentication succeeded
//	    } catch (NamingException e) {
//	        return false; // authentication failed
//	    } finally {
//	        if (ctx != null) {
//	            try { ctx.close(); } catch (NamingException e) {}
//	        }
//	    }
//	}
	
	

}

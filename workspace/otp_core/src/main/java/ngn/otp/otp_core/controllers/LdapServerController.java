package ngn.otp.otp_core.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ngn.otp.otp_core.models.ApplicationModel;
import ngn.otp.otp_core.models.LdapServerModel;
import ngn.otp.otp_core.services.LdapServerService;
import ngn.otp.otp_core.utils.CommonUtil;

@RestController
@RequestMapping(path = "/ldapserver")
public class LdapServerController {

	Logger logger = LoggerFactory.getLogger(UserController.class);
	private LdapServerService ldapServerService;
	public LdapServerController(LdapServerService ldapServerService) {
		super();
		this.ldapServerService = ldapServerService;
	}

	@GetMapping("/getAll")
	Map<String, Object> getAll(){
		logger.info("/ldapserver/getAll");
		return CommonUtil.createResult(0, "Success", ldapServerService.getAll());
	}

	@PostMapping("/add")
	Map<String,Object> add(@RequestBody Map<String, Object> requestBody) {
		logger.info("/ldapserver/add");
		int serverType;
		boolean defaultServer;
		String serverUrl, domainName, searchBase, searchFilter, serverDescription,principal,credential;
		LdapServerModel model = new LdapServerModel();
		try {
			serverUrl = requestBody.get("serverUrl").toString().trim();
			domainName = requestBody.get("domainName").toString().trim();
			searchBase = requestBody.get("searchBase").toString().trim();
			searchFilter = requestBody.get("searchFilter").toString().trim();
			serverDescription = requestBody.get("serverDescription").toString().trim();
			principal = requestBody.get("principal").toString().trim();
			credential = requestBody.get("credential").toString().trim();
			defaultServer =  (boolean)requestBody.get("defaultServer");
			serverType =  (int)requestBody.get("serverType");
			
			model.setServerUrl(serverUrl);
			model.setDomainName(domainName);
			model.setSearchBase(searchBase);
			model.setSearchFilter(searchFilter);
			model.setServerDescription(serverDescription);
			model.setPrincipal(principal);
			model.setCredential(credential);
			model.setDefaultServer(defaultServer);
			model.setServerType(serverType);


		}catch(Exception e) {
			return CommonUtil.createResult(400, "Missing field or invalid data type: "
					+ "{serverUrl:String, domainName:String, "
					+ "searchBase:String, searchFilter:String, "
					+ "serverDescription:String, principal:String, "
					+ "credential:String, serverType:int, "
					+ "defaultServer:boolean", null);
		}
		try {
			model=ldapServerService.save(model);
			return CommonUtil.createResult(200, "Ok", model);
		}catch(Exception e) {
			return CommonUtil.createResult(409, "Duplicated value", null);
		}
	}
	
	@PutMapping("/update/{serverId}")
	Map<String,Object> update(@PathVariable Long serverId, @RequestBody Map<String, Object> requestBody) {
		logger.info("/ldapserver/update/"+serverId);
		Integer serverType=null;
		Boolean defaultServer=null;
		String serverUrl=null, domainName=null, searchBase=null, searchFilter=null, 
				serverDescription=null,principal=null,credential=null;
		LdapServerModel model = ldapServerService.findById(serverId);
		if(model==null) {
			return CommonUtil.createResult(404, "serverId not found", null);
		}
		//get body parameter
		try {
			serverUrl = requestBody.get("serverUrl").toString().trim();
			if(serverUrl.isEmpty()) {
				return CommonUtil.createResult(400, "serverUrl could not be empty", null);
			}
		}catch(Exception e) {}
		
		try {
			domainName = requestBody.get("domainName").toString().trim();
		}catch(Exception e) {}
		
		try {
			searchBase = requestBody.get("searchBase").toString().trim();
		}catch(Exception e) {}
		
		try {
			searchFilter = requestBody.get("searchFilter").toString().trim();
		}catch(Exception e) {}
		
		try {
			serverDescription = requestBody.get("serverDescription").toString().trim();
		}catch(Exception e) {}
		
		try {
			principal = requestBody.get("principal").toString().trim();
		}catch(Exception e) {}
		
		try {
			credential = requestBody.get("credential").toString().trim();
		}catch(Exception e) {}
		
		try {
			defaultServer =  (boolean)requestBody.get("defaultServer");
		}catch(Exception e) {}
		
		try {
			serverType =  (int)requestBody.get("serverType");
		}catch(Exception e) {}
		
		
		//set to model
		if(serverUrl!=null) {
			if(serverUrl.isEmpty()==false) {
				model.setServerUrl(serverUrl);
			}
		}
		if(domainName!=null) {
			model.setDomainName(domainName);
		}
		if(searchBase!=null) {
			model.setSearchBase(searchBase);
		}
		if(searchFilter!=null) {
			model.setSearchFilter(searchFilter);
		}
		if(serverDescription!=null) {
			model.setServerDescription(serverDescription);
		}
		if(principal!=null) {
			model.setPrincipal(principal);
		}
		if(credential!=null) {
			model.setCredential(credential);
		}
		if(defaultServer!=null) {
			model.setDefaultServer(defaultServer);
		}
		if(serverType!=null) {
			model.setServerType(serverType);
		}
		
		try {
			model=ldapServerService.save(model);
			return CommonUtil.createResult(200, "Ok", model);
		}catch(Exception e) {
			return CommonUtil.createResult(409, "duplicated entry: {serverUrl}", null);
		}
		
	}
	
	@DeleteMapping("/delete")
	Map<String,Object> delete(@RequestBody Map<String, Object> requestBody) {
		logger.info("/ldapserver/delete");
		Integer serverId=null;
		try {
			serverId = (int)requestBody.get("serverId");
			LdapServerModel model = ldapServerService.findById((long)serverId);
			
			if(model!=null) {
				ldapServerService.delete(model);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(400,"ServerId not existed", null);
			}
			
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, e.toString(), null);
		}
	}

}

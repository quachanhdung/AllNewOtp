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
		return CommonUtil.createResult(200, "Success", ldapServerService.getAll());
	}

	@PostMapping("/add")
	Map<String,Object> add(@RequestBody Map<String, Object> requestBody) {
		logger.info("/ldapserver/add");
	
		boolean defaultServer;
		String serverUrl, searchBase, queryFilter, userDn, resultAttributes, serverDescription;
		LdapServerModel model = new LdapServerModel();
		try {
			serverUrl = requestBody.get("serverUrl").toString().trim();
		
			searchBase = requestBody.get("searchBase").toString().trim();
			queryFilter = requestBody.get("queryFilter").toString().trim();
			userDn = requestBody.get("userDn").toString().trim();
			resultAttributes = requestBody.get("resultAttributes").toString().trim();
			serverDescription = requestBody.get("serverDescription").toString().trim();
			defaultServer =  (boolean)requestBody.get("defaultServer");

			
			model.setServerUrl(serverUrl);
		
			model.setSearchBase(searchBase);
			model.setQueryFilter(queryFilter);
			model.setResultAttributes(resultAttributes);
			model.setUserDn(userDn);
			model.setServerDescription(serverDescription);
			model.setDefaultServer(defaultServer);
		


		}catch(Exception e) {
			logger.error(e.toString());
			return CommonUtil.createResult(400, "Missing field or invalid data type: "
					+ "{serverUrl:String, "
					+ "searchBase:String, queryFilter:String, userDn:String, resultAttributes:String "
					+ "serverDescription:String, serverType:int"
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
		Boolean defaultServer=null;
		String serverUrl=null,  searchBase=null, queryFilter=null, userDn=null,
				serverDescription=null, resultAttributes=null;
		
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
		}catch(Exception e) {
			
		}
		
		
		try {
			searchBase = requestBody.get("searchBase").toString().trim();
		}catch(Exception e) {}
		
		try {
			queryFilter = requestBody.get("queryFilter").toString().trim();
		}catch(Exception e) {}
		
		
		try {
			resultAttributes = requestBody.get("resultAttributes").toString().trim();
		}catch(Exception e) {}
		
	
		try {
			userDn = requestBody.get("userDn").toString().trim();
		}catch(Exception e) {}
		
		try {
			serverDescription = requestBody.get("serverDescription").toString().trim();
		}catch(Exception e) {}
		
		
		try {
			defaultServer =  (boolean)requestBody.get("defaultServer");
		}catch(Exception e) {}
		
		//set to model
		if(serverUrl!=null) {
			if(serverUrl.isEmpty()==false) {
				model.setServerUrl(serverUrl);
			}
		}
		
		if(searchBase!=null) {
			model.setSearchBase(searchBase);
		}
		if(queryFilter!=null) {
			model.setQueryFilter(queryFilter);
		}
		
		if(resultAttributes!=null) {
			model.setResultAttributes(resultAttributes);
		}
		if(userDn!=null) {
			model.setUserDn(userDn);
		}
		if(serverDescription!=null) {
			model.setServerDescription(serverDescription);
		}
		
		if(defaultServer!=null) {
			model.setDefaultServer(defaultServer);
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
			logger.error(e.toString());
			return CommonUtil.createResult(400, e.toString(), null);
		}
	}

}

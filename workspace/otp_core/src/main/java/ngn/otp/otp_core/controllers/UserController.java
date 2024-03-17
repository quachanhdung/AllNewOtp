package ngn.otp.otp_core.controllers;

import java.util.Date;
import java.util.HashMap;
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

import ngn.otp.otp_core.ApplicationContextProvider;
import ngn.otp.otp_core.models.ApplicationModel;
import ngn.otp.otp_core.models.UserApplicationModel;
import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.services.ApplicationService;
import ngn.otp.otp_core.services.DownloadPrivateKeyService;
import ngn.otp.otp_core.services.LdapServerService;
import ngn.otp.otp_core.services.UserApplicationService;
import ngn.otp.otp_core.services.UserService;
import ngn.otp.otp_core.utils.CommonUtil;
import ngn.otp.otp_core.utils.PropUtil;
import ngn.otp.otp_core.utils.TOTPUtil;

@RestController
@RequestMapping(path = "/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserService userService;
	private ApplicationService applicationService;
	private UserApplicationService userApplicationService;
	private LdapServerService ldapServerService;
	private DownloadPrivateKeyService downloadPrivateKeyService;
	PropUtil prop;
	private int privateKeyLength = 32;

	public UserController(
			UserService userService, 
			ApplicationService applicationService,
			LdapServerService ldapServerService,
			DownloadPrivateKeyService downloadPrivateKeyService,
			UserApplicationService userApplicationService) {
		this.userService=userService;
		this.applicationService = applicationService;
		this.ldapServerService = ldapServerService;
		this.userApplicationService = userApplicationService;
		this.downloadPrivateKeyService = downloadPrivateKeyService;
		this.prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);

		try {
			this.privateKeyLength = Integer.parseInt(prop.get("privatekey.length"));
		} catch (NumberFormatException e) {
			this.privateKeyLength=32;
		}
	}

	@GetMapping("/sayHello")
	Map<String, Object> sayHello(){
		logger.info("/user/sayHello");
		return CommonUtil.createResult(200, "Success", "hello, this is user controller");
	}

	@PostMapping("/add")
	Map<String,Object> add(@RequestBody Map<String, Object> requestBody) {
		logger.info("/user/add");
		String userId, email=null, phone=null, fullName=null;
		try {
			userId = requestBody.get("userId").toString();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "userId is required", null);
		}
		if(userId.trim().isEmpty()) {
			return CommonUtil.createResult(400, "userId is required", null);
		}
		//email
		try {
			email=requestBody.get("email").toString().trim();
			if(CommonUtil.isValidEmailAddress(email)==false) {
				email=null;
			}
		}catch(Exception e) {};
		//phone
		try {
			phone=requestBody.get("phone").toString().trim();
			if(CommonUtil.isValidPhoneNumber(phone)==false) {
				phone=null;
			}
		}catch(Exception e) {}
		//fullName
		try {
			fullName=requestBody.get("fullName").toString().trim();
		}catch(Exception e) {}

		try {
			UserModel userModel=userService.findById(userId);
			if(userModel==null) {
				userModel=new UserModel();
				userModel.setUserId(userId);
				userModel.setEmail(email);
				userModel.setFullName(fullName);
				userModel.setPrivateKey(TOTPUtil.generatePrivateKey(this.privateKeyLength));
				userModel.setActiveCode(TOTPUtil.generateRandomNumberString());
				userModel.setEnable(true);
				userModel.setDateCreated(new Date());
				userModel.setOrganization("Peoples");
				userService.save(userModel);
				return CommonUtil.createResult(200, "OK", userModel);
			}else {
				return CommonUtil.createResult(409, "User exist", null);
			}
		} catch (Exception e) {
			return CommonUtil.createResult(409,"duplicate entry: email or phone",null);
		}
	}

	@PutMapping("/updateInfo/{userId}")
	Map<String,Object> updateInfo(@PathVariable String userId, @RequestBody Map<String, Object> requestBody) {
		logger.info("/user/updateInfo");
		String  email=null, phone=null, fullName=null, organization=null,jobTitle=null, cccd=null;
		//email
		try {
			email=requestBody.get("email").toString().trim();
			if(CommonUtil.isValidEmailAddress(email)==false) {
				email=null;
			}
		}catch(Exception e) {};
		//phone
		try {
			phone=requestBody.get("phone").toString().trim();
			if(CommonUtil.isValidPhoneNumber(phone)==false) {
				phone=null;
			}
		}catch(Exception e) {}
		//fullName
		try {
			fullName=requestBody.get("fullName").toString().trim();
		}catch(Exception e) {}
		
		//organization
		try {
			organization=requestBody.get("organization").toString().trim();
		}catch(Exception e) {}
		
		//jobTitle
		try {
			jobTitle=requestBody.get("jobTitle").toString().trim();
		}catch(Exception e) {}
		//cccd
		try {
			cccd=requestBody.get("cccd").toString().trim();
		}catch(Exception e) {}
	
		try {
			UserModel userModel=userService.findById(userId);
			if(userModel!=null) {
				//user info
				userModel.setUserId(userId);
				if(email!=null) {
					userModel.setEmail(email);
				}
				if(phone!=null) {
					userModel.setPhone1(phone);
				}
				if(fullName!=null) {
					userModel.setFullName(fullName);
				}
				if(organization!=null) {
					userModel.setOrganization(organization);
				}
				if(jobTitle!=null) {
					userModel.setJobTitle(jobTitle);
				}
				if(cccd!=null) {
					userModel.setCccd(cccd);
				}
				
				userModel.setDateModified(new Date());
				
				userService.save(userModel);
				return CommonUtil.createResult(200, "OK", userModel);
			}else {
				return CommonUtil.createResult(401, "User not exist", null);
			}
		} catch (Exception e) {
			return CommonUtil.createResult(409, "duplicate entry: email or phone", null);
		}
	}

	@DeleteMapping("/delete")
	Map<String,Object> delete(@RequestBody Map<String, Object> requestBody) {
		logger.info("/user/delete");
		String userId;
		
		try {
			userId = requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			userApplicationService.deleteByUser(userModel);
			userService.delete(userModel);
			return CommonUtil.createResult(200, "Ok", null);
		}catch(Exception e) {
			return CommonUtil.createResult(400, e.toString(), null);
		}
	}
	
	@PostMapping("/get")
	Map<String, Object> get(@RequestBody Map<String, Object> requestBody){
		logger.info("user/get");
		String userId;
		try {
			userId=requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				return CommonUtil.createResult(200, "Ok", userModel);
			}else {
				return CommonUtil.createResult(401, "User not found", userModel);
			}
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}
		
	}
	
	@PostMapping("/getPrivateKey")
	Map<String, Object> getPrivateKey(@RequestBody Map<String, Object> requestBody){
		logger.info("user/getPrivateKey");
		String userId;
		try {
			userId=requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				return CommonUtil.createResult(200, "Ok", userModel.getPrivateKey());
			}else {
				return CommonUtil.createResult(401, "User not found", userModel);
			}
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}
		
	}
	@PostMapping("/getActiveCode")
	Map<String, Object> getActiveCode(@RequestBody Map<String, Object> requestBody){
		logger.info("user/getActiveCode");
		String userId;
		try {
			userId=requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				return CommonUtil.createResult(200, "Ok", userModel.getActiveCode());
			}else {
				return CommonUtil.createResult(401, "User not found", userModel);
			}
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}
		
	}

	@PutMapping("/enableOtp")
	Map<String, Object> enableOtp(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/enableOtp");
		String userId;
		boolean enable=false;
		try {
			userId=requestBody.get("userId").toString().trim();
			enable = (boolean) requestBody.get("enable");
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setEnable(enable);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
	}
	
	@PutMapping("/enableAppCode")
	Map<String, Object> enableAppCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/enableAppCode");
		String userId;
		boolean enable=false;
		try {
			userId=requestBody.get("userId").toString().trim();
			enable = (boolean) requestBody.get("enable");
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setEnableAppCode(enable);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
	}
	@PutMapping("/disableOtpApp")
	Map<String, Object> disableOtpApp(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/disableOtpApp");
		String userId;
		
		try {
			userId=requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setEnableOtpApp(false);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				downloadPrivateKeyService.deleteByUserId(userId);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}
	}
	
	@PutMapping("/enableRequired")
	Map<String, Object> enableRequired(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/enableRequired");
		String userId;
		boolean enable=false;
		try {
			userId=requestBody.get("userId").toString().trim();
			enable = (boolean) requestBody.get("enable");
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setRequired(enable);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
	}
	

	@PutMapping("/enableSms")
	Map<String, Object> enableSms(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/enableSms");
		String userId;
		boolean enable=false;
		try {
			userId=requestBody.get("userId").toString().trim();
			enable = (boolean) requestBody.get("enable");
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setEnableSms(enable);
				if(enable) {
					userModel.setEnable(true);
				}
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
	}
	
	@PutMapping("/enableAdmin")
	Map<String, Object> enableAdmin(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/enableAdmin");
		String userId;
		boolean enable=false;
		try {
			userId=requestBody.get("userId").toString().trim();
			enable = (boolean) requestBody.get("enable");
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setIsAdmin(enable);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
	}
	
	/**
	 * Check if user enabled otp or otp for application
	 * @param requestBody {userId,applicationId}. if applicationId is null 
	 * @return true or false
	 */
	@PostMapping("/checkOtp")
	Map<String ,Object> checkOtp(@RequestBody Map<String,Object> requestBody){
		logger.info("/user/authOtpCode");
		String userId=null, applicationId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
		
		try {
			applicationId=requestBody.get("applicationId").toString().trim();
		}catch(Exception e) {
		}
		
		try {
			UserModel userModel = userService.findById(userId);
			if(userModel==null) {
				Map<String, Object> result=new HashMap<>();
				result.put("enableOtp", false);
				return CommonUtil.createResult(200, "User not exist", result);
			}
			
			if(applicationId==null) {
				Map<String, Object> result=new HashMap<>();
				result.put("enableOtp", userModel.getEnable());
				return CommonUtil.createResult(200, "Ok", result);
			}
			
			ApplicationModel applicationModel = applicationService.findById(applicationId);
			if(applicationModel==null) {
				Map<String, Object> result=new HashMap<>();
				result.put("enableOtp", false);
				return CommonUtil.createResult(200, "Application not exist", result);
			}
			
			UserApplicationModel userApplicationModel = userApplicationService.findByUserAndApplicationModel(userModel,applicationModel);
			if(userApplicationModel==null) {
				Map<String, Object> result=new HashMap<>();
				result.put("enableOtp", false);
				return CommonUtil.createResult(200, "User not in application", result);
			}
			Map<String, Object> result=new HashMap<>();
			result.put("enableOtp", userModel.getEnable());
			return CommonUtil.createResult(200, "Ok", result);
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, enable: boolean} is required", null);
		}
	}
	
	/**
	 * Check if userId and otpCode are match
	 * @param requestBody: userId and otpCode
	 * @return Auth: true or false
	 */
	@PostMapping("/authOtpCode")
	Map<String ,Object> authOtpCode(@RequestBody Map<String,Object> requestBody){
		logger.info("/user/authOtpCode");
		String userId, otpCode;
		
		try {
			userId=requestBody.get("userId").toString().trim();
			otpCode=requestBody.get("otpCode").toString().trim();
			
			UserModel userModel = userService.findById(userId);
			if(userModel==null) {
				return CommonUtil.createResult(401, "User not exist ", null);
			}
			boolean authen = TOTPUtil.checkOTP(userModel.getPrivateKey(), otpCode);
			logger.info("authOtpCode "+userId+" "+authen);
			if(authen) {
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "OtpCode not match", null);
			}
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, otpCode: String} is required", null);
		}
		
	}
	
	@PostMapping("/authLdap")
	Map<String, Object> authLdap(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/authLdap");
		String userId, password;
		try {
			userId=requestBody.get("userId").toString().trim();
			password=requestBody.get("password").toString().trim();
			boolean authen = ldapServerService.authen(userId, password);
			logger.info("authLdap "+userId+" "+authen);
			if(authen) {
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "userId or password not match", null);
			}
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, password: String} is required", null);
		}
	}
	
	

}

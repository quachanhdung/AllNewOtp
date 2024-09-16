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
import ngn.otp.otp_core.services.EmailService;
import ngn.otp.otp_core.services.LdapServerService;
import ngn.otp.otp_core.services.SmsService;
import ngn.otp.otp_core.services.SmsVnptService;
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
	private SmsService smsService ;
	private SmsVnptService smsVnptService;
	private EmailService emailService;
	PropUtil prop;
	private int privateKeyLength = 32;

	public UserController(
			UserService userService, 
			ApplicationService applicationService,
			LdapServerService ldapServerService,
			DownloadPrivateKeyService downloadPrivateKeyService,
			SmsService smsService,
			SmsVnptService smsVnptService,
			EmailService emailService,
			UserApplicationService userApplicationService) {
		this.userService=userService;
		this.applicationService = applicationService;
		this.ldapServerService = ldapServerService;
		this.userApplicationService = userApplicationService;
		this.downloadPrivateKeyService = downloadPrivateKeyService;
		this.smsService = smsService;
		this.smsVnptService = smsVnptService;
		this.emailService = emailService;
		this.prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);

		try {
			this.privateKeyLength = Integer.parseInt(prop.get("privatekey.length"));
		} catch (NumberFormatException e) {
			this.privateKeyLength=32;
		}
	}

	@GetMapping("/getAll")
	Map<String, Object> getAll(){
		logger.info("/user/getAll");
		return CommonUtil.createResult(0, "Success", userService.getAll());
	}

	@PostMapping("/search")
	Map<String, Object> search(@RequestBody Map<String, Object> requestBody){
		int offset=0, limit =100;
		String keyword = "";
		try {
			offset= (int) requestBody.get("offset");
			
		}catch(Exception e) {
			
		}
		
		try {
			limit = (int) requestBody.get("limit");
		} catch (Exception e) {
			
			
		}
		try {
			keyword = requestBody.get("keyword").toString();
		} catch (Exception e) {
		
			
		}
		
		Map<String,Object> result = new HashMap<>();
		result = CommonUtil.createResult(0, "Success", userService.search(keyword, offset, limit));
		long count = userService.searchCount(keyword);
		result.put("count", count);
		
		
		return result;
	}



	@PostMapping("/add")
	Map<String,Object> add(@RequestBody Map<String, Object> requestBody) {
		logger.info("/user/add");
		String userId=null, email=null, phone=null, fullName="";
		try {
			userId = requestBody.get("userId").toString().trim();
			phone=requestBody.get("phone").toString().trim();
			email=requestBody.get("email").toString().trim();
			if(CommonUtil.isValidEmailAddress(email)==false) {
				email="";
			}
			if(CommonUtil.isValidPhoneNumber(phone)==false) {
				phone="";
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "userId, phone, email are required", null);
		}

		if(userId.isEmpty() || phone.isEmpty() || email.isEmpty()) {
			return CommonUtil.createResult(400, "userId, phone, email are required", null);
		}


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
				userModel.setPhone1(phone);
				userModel.setFullName(fullName);
				userModel.setPrivateKey(TOTPUtil.generatePrivateKey(this.privateKeyLength));
				userModel.setActiveCode(TOTPUtil.generateRandomNumberString());
				userModel.setEnable(true);
				userModel.setDateCreated(new Date());
				userModel.setOrganization("Peoples");
				userModel.setEnableSms(true);
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
			downloadPrivateKeyService.deleteByUserId(userId);
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
	@PutMapping("/setPinCode")
	Map<String, Object> enablePinCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/setPinCode");
		String userId;
		String pinCode;
		int pinCodeLength = 6;
		try {
			pinCodeLength = Integer.parseInt(prop.get("pincode.length"));
		} catch (Exception e) {
		}
		
		try {
			userId=requestBody.get("userId").toString().trim();
			pinCode =  requestBody.get("pinCode").toString().trim();
			
			try {
				Integer.parseInt(pinCode);
			} catch (Exception e) {
				return CommonUtil.createResult(401, "pinCode must be a string containing "+pinCodeLength+" digit", null);
			}
			
			
			
			
			if(pinCode.length()!=pinCodeLength){
				return CommonUtil.createResult(401, "pinCode must be a string containing "+pinCodeLength+" digit", null);
			}
			
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setPinCode(pinCode);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, pinCode: String} is required", null);
		}
	}
	
	@PutMapping("/clearPinCode")
	Map<String, Object> clearPinCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/clearPinCode");
		String userId;
		try {
			userId=requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			if(userModel!=null) {
				userModel.setPinCode(null);
				userModel.setDateModified(new Date());
				userService.save(userModel);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(401, "User not found", null);
			}
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}
	}
	@PutMapping("/disableOtpApp")
	Map<String, Object> disableOtpApp(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/disableOtpApp");
		String userId;

		try{
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		try {
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
			return CommonUtil.createResult(400, e.toString(),null);
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
			Map<String,Object> authen = ldapServerService.authen(userId, password);
			logger.info("authLdap "+userId+" "+authen);
			if(authen!=null) {
				//insert user to database if not exists
				addUser(userId,authen);

				
				return CommonUtil.createResult(200, "Ok", authen);
			}else {
				return CommonUtil.createResult(401, "userId or password not match", null);
			}

		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String, password: String} is required", null);
		}
	}


	private void addUser(String userId, Map<String, Object> authen) {
		if (userId.contains("@"))
			userId = userId.substring(0, userId.indexOf("@"));
		// domain\\username
		if (userId.contains("\\"))
			userId = userId.substring(userId.indexOf("\\") + 1);

		String phone="";
		String email="";
		String fullName="";
		for (String key : authen.keySet()) {
	        System.out.println(key + ":" + authen.get(key));
	        
	        if(key.equals(prop.get("ldap.UserFullNameAttName"))) {
	        	fullName = authen.get(key).toString();
	        }
	        
	        if(key.equals(prop.get("ldap.PhoneAttName"))) {
	        	phone=authen.get(key).toString();
	        }
	        
	        if(key.equals(prop.get("ldap.EmailAttName"))) {
	        	email=authen.get(key).toString();
	        }
	        
	    }
		
		UserModel userModel=userService.findById(userId);
		if(userModel==null) {
			userModel=new UserModel();
			userModel.setUserId(userId);
			userModel.setEmail(email);
			userModel.setPhone1(phone);
			userModel.setFullName(fullName);
			userModel.setPrivateKey(TOTPUtil.generatePrivateKey(this.privateKeyLength));
			userModel.setActiveCode(TOTPUtil.generateRandomNumberString());
			userModel.setEnable(true);
			userModel.setDateCreated(new Date());
			userModel.setOrganization("Peoples");
			userModel.setEnableSms(true);
			
		}else {
			if(email.isEmpty()==false) {
				userModel.setEmail(email);
			}
			if(phone.isEmpty()==false) {
				userModel.setPhone1(phone);
			}
		
			if(fullName.isEmpty()==false) {
				userModel.setFullName(fullName);
			}
		
		}
		userService.save(userModel);
	
		
		
	}

	@PostMapping("/sendSms")
	Map<String, Object> sendSms(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendSMS");
		String recipent, content;
		try {
			recipent=requestBody.get("recipent").toString().trim();
			content=requestBody.get("content").toString().trim();
//			smsService.sendSms(recipent, content);
			sendSMS(recipent, content+ " Tran trong");
			return CommonUtil.createResult(200, "Ok", null);

		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {recipent: String, content: String} is required", null);
		}
	}

	@PostMapping("/sendSmsPrivateKey")
	Map<String, Object> sendSmsPrivateKey(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendSmsPrivateKey");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		try {
//			smsService.sendSms(userModel.getPhone1(), userModel.getPrivateKey());
			sendSMS(userModel.getPhone1(),userModel.getPrivateKey() + " Tran trong");
			logger.info("/user/sendSmsPrivateKey: Ok  "+userId+"  "+userModel.getPhone1());
			return CommonUtil.createResult(200, "Ok", null);
		} catch (Exception e) {
			logger.error("/user/sendSmsPrivateKey: Fail  "+userId+"  "+userModel.getPhone1());
			return CommonUtil.createResult(400, "Send SMS eror", e.toString());
		}
	}

	@PostMapping("/sendSmsActiveCode")
	Map<String, Object> sendSmsActiveCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendSmsActiveCode");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		try {
//			smsService.sendSms(userModel.getPhone1(), "Active code: "+userModel.getActiveCode());
			sendSMS(userModel.getPhone1(), "Active code: "+userModel.getActiveCode()+ " Tran trong");
			logger.info("/user/sendSmsActiveCode: Ok  "+userId+"  "+userModel.getPhone1());
			return CommonUtil.createResult(200, "Ok", null);
		} catch (Exception e) {
			logger.error("/user/sendSmsActiveCode: Fail  "+userId+"  "+userModel.getPhone1());
			return CommonUtil.createResult(400, "Send SMS eror", e.toString());
		}
	}

	@PostMapping("/sendSmsOtpCode")
	Map<String, Object> sendSmsOtpCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendSmsOtpCode");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		if(userModel.getEnableSms()==false) {
			return CommonUtil.createResult(400, "User not enable sms", null);
		}

		try {
			logger.info(userModel.getPrivateKey());
			String otpCode =TOTPUtil.getCode(userModel.getPrivateKey(), TOTPUtil.getSteps(1));
//			smsService.sendSms(userModel.getPhone1(), "OTP: "+otpCode);
			sendSMS(userModel.getPhone1(),"OTP: "+otpCode+ " Tran trong");
			logger.info("/user/sendSmsOtpCode: Ok  "+userId+"  "+userModel.getPhone1());
			return CommonUtil.createResult(200, "Ok", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("/user/sendSmsOtpCode: Fail  "+userId+"  "+userModel.getPhone1());
			return CommonUtil.createResult(400, "Send SMS eror", e.toString());
		}
	}

	@PostMapping("/sendEmailPrivateKey")
	Map<String, Object> sendEmailPrivateKey(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendEmailPrivateKey");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		try {
			new Thread(()->{
				logger.info("sending email private key to "+userModel.getEmail());
				emailService.sendEmail(userModel.getEmail(), "Your private key", "This is your private key: "+userModel.getPrivateKey());

			}).start();

			logger.info("/user/sendEmailPrivateKey: Ok  "+userId+"  "+userModel.getEmail());
			return CommonUtil.createResult(200, "Ok", null);
		} catch (Exception e) {
			logger.error("/user/sendEmailPrivateKey: Fail  "+userId+"  "+userModel.getEmail());
			return CommonUtil.createResult(400, "Send email eror", e.toString());
		}
	}

	@PostMapping("/sendEmailActiveCode")
	Map<String, Object> sendEmailActiveCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendEmailActiveCode");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		try {
			new Thread(()->{
				logger.info("sending email active code to: "+userModel.getUserId()+" "+userModel.getEmail());
				emailService.sendEmail(userModel.getEmail(), "Your active code", "This is your active code: "+userModel.getActiveCode());

			}).start();

			logger.info("/user/sendEmailActiveCode: Ok  "+userModel.getUserId()+"  "+userModel.getEmail());
			return CommonUtil.createResult(200, "Ok", null);
		} catch (Exception e) {
			logger.error("/user/sendEmailActiveCode: Fail  "+userModel.getUserId()+"  "+userModel.getEmail());
			return CommonUtil.createResult(400, "Send email eror", e.toString());
		}
	}

	@PostMapping("/sendEmailOtpCode")
	Map<String, Object> sendEmailOtpCode(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/sendEmailOtpCode");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		try {
			String otpCode =TOTPUtil.getCode(userModel.getPrivateKey(), TOTPUtil.getSteps(1));
			new Thread(()->{
				logger.info("sending email otp code to: "+userModel.getUserId()+" "+userModel.getEmail());
				emailService.sendEmail(userModel.getEmail(), "Your otp code", "This is your otp code: "+otpCode);
			}).start();
			logger.info("/user/sendEmailOtpCode: Ok  "+userModel.getUserId()+"  "+userModel.getEmail());
			return CommonUtil.createResult(200, "Ok", null);
		} catch (Exception e) {
			logger.error("/user/sendEmailOtpCode: Fail  "+userModel.getUserId()+"  "+userModel.getEmail());
			return CommonUtil.createResult(400, "Send email eror", e.toString());
		}
	}

	@PostMapping("/checkRequireOtp")
	Map<String, Object> checkRequireOtp(@RequestBody Map<String, Object> requestBody){
		logger.info("/user/checkRequireOtp");
		String userId=null;
		try {
			userId=requestBody.get("userId").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		if(userId.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: {userId: String} is required", null);
		}

		UserModel userModel = userService.findById(userId);
		if(userModel==null) {
			return CommonUtil.createResult(404, "User not found", null);
		}

		try {
			Map<String, Object> result=new HashMap<>();
			result.put("requireOtp", userModel.getRequired());
			return CommonUtil.createResult(200, "Ok", result);
		} catch (Exception e) {

			return CommonUtil.createResult(400, "Send email eror", e.toString());
		}
	}

	private void sendSMS(String recipent, String content) {
		if(prop.get("sms.provider").equalsIgnoreCase("vnpt")) {
			this.smsVnptService.sendSMS(recipent, content);
		}else {
			this.smsService.sendSms(recipent, content);
		}
	}






}

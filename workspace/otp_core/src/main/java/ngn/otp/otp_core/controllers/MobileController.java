package ngn.otp.otp_core.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ngn.otp.otp_core.models.DownloadPrivateKeyModel;
import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.services.DownloadPrivateKeyService;
import ngn.otp.otp_core.services.UserService;
import ngn.otp.otp_core.utils.AES;
import ngn.otp.otp_core.utils.CommonUtil;

@RestController
@RequestMapping(path = "/mobile")
public class MobileController {
	
	Logger logger = LoggerFactory.getLogger(MobileController.class);
	private UserService userService;
	private DownloadPrivateKeyService downloadPrivateKeyService;
	
	public MobileController(UserService userService, DownloadPrivateKeyService downloadPrivateKeyService) {
		this.userService = userService;
		this.downloadPrivateKeyService = downloadPrivateKeyService;
	}
	/**
	 * Download private key to app using activeCode
	 * @param requestBody
	 * deviceId , activeCode
	 * @return private key encrypted with salt is deviceId
	 */
	@PostMapping("/downloadPrivateKeyByActiveCode")
	Map<String, Object> downloadPrivateKeyByActiveCode(@RequestBody Map<String, Object> requestBody){

		logger.info("/mobile/downloadPrivateKeyByActiveCode");
		String deviceId=null, activeCode =null, deviceName="";
		try {
			 deviceId = requestBody.get("deviceId").toString().trim();
			 activeCode = requestBody.get("activeCode").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: deviceId and activeCode are required", null);
		}
		
		try {
			deviceName = requestBody.get("deviceName").toString().trim();
		}catch(Exception e) {}
		
		if(deviceId.isEmpty() || activeCode.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: deviceId and activeCode are required", null);
		}
		
		UserModel userModel = userService.findByActiveCode(activeCode);
		if(userModel==null) {
			return CommonUtil.createResult(404, "activeCode not found", null);
		}
		
		if(userModel.getEnableOtpApp()==true) {
			return CommonUtil.createResult(404, "User already downloaded private key", null);
		}
		
		String salt = deviceId;
		if(salt.length() >=32) {
			salt = salt.substring(0, 32);
		}else {
			while(salt.length()<32) {
				salt = salt+salt;
			}
			salt = salt.substring(0, 32);
		}
		
		AES aes = new AES();
		String privateKey=aes.encrypt(userModel.getPrivateKey(), salt);
		Map<String,Object> result = new HashMap<>();
		result.put("privateKey", privateKey);
		
		DownloadPrivateKeyModel model = new DownloadPrivateKeyModel(userModel,deviceId,deviceName) ;
		downloadPrivateKeyService.save(model);
		userModel.setEnableOtpApp(true);
		userService.save(userModel);
		
		return CommonUtil.createResult(200, "Ok",result );
		
	}
	
	/**
	 * Download private key to app using phone
	 * @param requestBody
	 * deviceId , phone
	 * @return private key encrypted with salt is deviceId
	 */
	@PostMapping("/downloadPrivateKeyByPhone")
	Map<String, Object> downloadPrivateKeyByPhone(@RequestBody Map<String, Object> requestBody){
		logger.info("/mobile/downloadPrivateKeyByPhone");

		String deviceId=null, phone =null, deviceName="";
		try {
			 deviceId = requestBody.get("deviceId").toString().trim();
			 phone = requestBody.get("phone").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: deviceId and phone are required", null);
		}
		
		if(deviceId.isEmpty() || phone.isEmpty() ) {
			return CommonUtil.createResult(400, "Bad request: deviceId and phone are required", null);
		}
		
		try {
			deviceName = requestBody.get("deviceName").toString().trim();
		}catch(Exception e) {}
		
		if(CommonUtil.isValidPhoneNumber(phone)==false) {
			return CommonUtil.createResult(400, "Bad request: invalid phone number", null);
		}
		
		
		
		UserModel userModel = userService.findByPhone(phone);
		if(userModel==null) {
			return CommonUtil.createResult(404, "Phone not found", null);
		}
		if(userModel.getEnableOtpApp()==true) {
			return CommonUtil.createResult(404, "User already downloaded private key", null);
		}
		
		String salt = deviceId;
		if(salt.length() >=32) {
			salt = salt.substring(0, 32);
		}else {
			while(salt.length()<32) {
				salt = salt+salt;
			}
			salt = salt.substring(0, 32);
		}
		
		AES aes = new AES();
		String privateKey=aes.encrypt(userModel.getPrivateKey(), salt);
		Map<String,Object> result = new HashMap<>();
		result.put("privateKey", privateKey);
		
		DownloadPrivateKeyModel model = new DownloadPrivateKeyModel(userModel,deviceId,deviceName) ;
		downloadPrivateKeyService.save(model);
		userModel.setEnableOtpApp(true);
		userService.save(userModel);
		
		return CommonUtil.createResult(200, "Ok",result );
		
	}
	
	/**
	 * Download private key to app using email
	 * @param requestBody
	 * deviceId , email
	 * @return private key encrypted with salt is deviceId
	 */
	@PostMapping("/downloadPrivateKeyByEmail")
	Map<String, Object> downloadPrivateKeyByEmail(@RequestBody Map<String, Object> requestBody){
		logger.info("/mobile/downloadPrivateKeyByEmail");

		String deviceId=null, email =null, deviceName="";
		try {
			 deviceId = requestBody.get("deviceId").toString().trim();
			 email = requestBody.get("email").toString().trim();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "Bad request: deviceId and email are required", null);
		}
		
		if(deviceId.isEmpty() || email.isEmpty()) {
			return CommonUtil.createResult(400, "Bad request: deviceId and email are required", null);
		}
		
		if(CommonUtil.isValidEmailAddress(email)==false) {
			return CommonUtil.createResult(400, "Bad request: Invalid email address", null);
		}
		
		try {
			deviceName = requestBody.get("deviceName").toString().trim();
		}catch(Exception e) {}
		
		UserModel userModel = userService.findByEmail(email);
		if(userModel==null) {
			return CommonUtil.createResult(404, "Email not found", null);
		}
		if(userModel.getEnableOtpApp()==true) {
			return CommonUtil.createResult(404, "User already downloaded private key", null);
		}
		
		String salt = deviceId;
		if(salt.length() >=32) {
			salt = salt.substring(0, 32);
		}else {
			while(salt.length()<32) {
				salt = salt+salt;
			}
			salt = salt.substring(0, 32);
		}
		
		AES aes = new AES();
		String privateKey=aes.encrypt(userModel.getPrivateKey(), salt);
		Map<String,Object> result = new HashMap<>();
		result.put("privateKey", privateKey);
		
		DownloadPrivateKeyModel model = new DownloadPrivateKeyModel(userModel,deviceId,deviceName) ;
		downloadPrivateKeyService.save(model);
		userModel.setEnableOtpApp(true);
		userService.save(userModel);
		
		return CommonUtil.createResult(200, "Ok",result );
		
	}
	
	
	
	
}

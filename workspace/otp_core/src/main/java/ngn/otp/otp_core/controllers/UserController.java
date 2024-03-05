package ngn.otp.otp_core.controllers;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ngn.otp.otp_core.ApplicationContextProvider;
import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.services.UserService;
import ngn.otp.otp_core.utils.CommonUtil;
import ngn.otp.otp_core.utils.PropUtil;
import ngn.otp.otp_core.utils.TOTPUtil;

@RestController
@RequestMapping(path = "/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	private UserService userService;
	PropUtil prop;
	private int privateKeyLength = 32;
	
	public UserController(UserService userService) {
		this.userService=userService;
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
	Map<String,Object> register(@RequestBody Map<String, Object> requestBody) {
		String userId, email="", phone="";
		try {
			 userId = requestBody.get("userId").toString();
		}catch(Exception e) {
			return CommonUtil.createResult(999, e.toString(), null);
		}
		
		try {
			 email=requestBody.get("email").toString();
			 phone=requestBody.get("phone").toString();
		}catch(Exception e) {
			
		}
		
		UserModel userModel=userService.findById(userId);
		if(userModel==null) {
			userModel=new UserModel();
			userModel.setUserId(userId);
			userModel.setEmail(email);
			userModel.setPhone1(phone);
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
	}

}

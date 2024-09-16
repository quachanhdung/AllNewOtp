package ngn.otp.otp_core.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ngn.otp.otp_core.models.ApplicationModel;
import ngn.otp.otp_core.models.UserApplicationId;
import ngn.otp.otp_core.models.UserApplicationModel;
import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.services.ApplicationService;
import ngn.otp.otp_core.services.UserApplicationService;
import ngn.otp.otp_core.services.UserService;
import ngn.otp.otp_core.utils.CommonUtil;

@RestController
@RequestMapping(path = "/userapplication")
public class UserApplicationController {
	Logger logger = LoggerFactory.getLogger(UserApplicationController.class);
	
	private UserApplicationService userApplicationService;
	private UserService userService;
	private ApplicationService applicationService;
	
	public UserApplicationController(
			UserApplicationService userApplicationService,
			UserService userService,
			ApplicationService applicationService) {
		this.userApplicationService = userApplicationService;
		this.userService=userService;
		this.applicationService=applicationService;
		
	}
	
	@GetMapping("/getAll")
	Map<String, Object> getAll(){
		logger.info("/userapplication/getAll");
		return CommonUtil.createResult(200, "OK", userApplicationService.getAll());
	}
	
	@PostMapping("/getByUser")
	Map<String, Object> getByUser(@RequestBody Map<String, Object> requestBody){
		logger.info("/userapplication/getByUser");
		String userId;
		try {
			userId=requestBody.get("userId").toString().trim();
			UserModel userModel = userService.findById(userId);
			if(userModel==null) {
				return CommonUtil.createResult(400, "user not found", null);
			}
			return CommonUtil.createResult(200, "OK", userApplicationService.findByUser(userModel));
		}catch(Exception e) {
			return CommonUtil.createResult(400, "userId is required", null);
		}
	}
	@PostMapping("/getByApplication")
	Map<String, Object> getByApplication(@RequestBody Map<String, Object> requestBody){
		logger.info("/userapplication/getByApplication");
		String applicationId;
		try {
			applicationId=requestBody.get("applicationId").toString().trim();
			ApplicationModel applicationModel = applicationService.findById(applicationId);
			if(applicationModel==null) {
				return CommonUtil.createResult(400, "application not found", null);
			}
			return CommonUtil.createResult(200, "OK", userApplicationService.findByApplicationModel(applicationModel));
		}catch(Exception e) {
			return CommonUtil.createResult(400, "applicationId is required", null);
		}
	}

	@PostMapping("/add")
	Map<String, Object> add(@RequestBody Map<String, Object> requestBody){
		logger.info("/userapplication/add");
		String userId, applicationId;
		try {
			userId = requestBody.get("userId").toString();
			applicationId = requestBody.get("applicationId").toString();
		} catch (Exception e) {
			return CommonUtil.createResult(400, "userId and applicationId is required", e.toString());
		}
		
		try {
			UserApplicationId userApplicationId=new UserApplicationId(userId,applicationId);
			UserModel userModel = userService.findById(userId);
			if(userModel==null) {
				return CommonUtil.createResult(400, "User does not exist", null);
			}
			ApplicationModel applicationModel = applicationService.findById(applicationId);
			if(applicationModel==null) {
				return CommonUtil.createResult(400, "Application does not exist", null);
			}
			UserApplicationModel model = new UserApplicationModel(userApplicationId,userModel,applicationModel);
			userApplicationService.save(model);
			return CommonUtil.createResult(200, "Ok", model);
		} catch (Exception e) {
			return CommonUtil.createResult(400, e.toString(), e.toString());
		}
		
	}

}

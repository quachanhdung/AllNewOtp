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
import ngn.otp.otp_core.models.UserModel;
import ngn.otp.otp_core.services.ApplicationService;
import ngn.otp.otp_core.services.UserApplicationService;
import ngn.otp.otp_core.utils.CommonUtil;
import ngn.otp.otp_core.utils.TOTPUtil;

@RestController
@RequestMapping(path = "/application")
public class ApplicationController {
	
	Logger logger = LoggerFactory.getLogger(ApplicationController.class);
	private ApplicationService applicationService;
	private UserApplicationService userApplicationService;
	
	public ApplicationController(ApplicationService applicationService, UserApplicationService userApplicationService) {
		this.applicationService = applicationService;
		this.userApplicationService = userApplicationService;
	}
	
	@GetMapping("/getAll")
	Map<String, Object> getAll(){
		logger.info("/application/getAll");
		return CommonUtil.createResult(0, "Success", applicationService.getAll());
	}
	
	@PostMapping("/add")
	Map<String,Object> add(@RequestBody Map<String, Object> requestBody) {
		logger.info("/application/add");
		String applicationName, description;
		try {
			applicationName = requestBody.get("applicationName").toString();
			description = requestBody.get("description").toString();
		}catch(Exception e) {
			return CommonUtil.createResult(400, "applicationName and description is required", null);
		}
		
		if(applicationName.trim().isEmpty() || description.trim().isEmpty()) {
			return CommonUtil.createResult(400, "applicationName and description is required", null);
		}
		
		try {
			ApplicationModel applicationModel=applicationService.findByApplicationName(applicationName);
			
			if(applicationModel==null) {
				applicationModel= new ApplicationModel();
				applicationModel.setApplicationId(TOTPUtil.generateSecret());
				applicationModel.setApplicationName(applicationName);
				applicationModel.setDescription(description);
				applicationService.add(applicationModel);
				return CommonUtil.createResult(200, "OK", applicationModel);
			}else {
				return CommonUtil.createResult(409, "Application name existed", null);
			}
		} catch (Exception e) {
			return CommonUtil.createResult(409,"duplicate entry: id or name",null);
		}
	}
	@PutMapping("/update/{applicationId}")
	Map<String,Object> update(@PathVariable String applicationId, @RequestBody Map<String, Object> requestBody) {
		logger.info("/application/update/"+applicationId);
		String applicationName=null, description=null;
		try {
			applicationName = requestBody.get("applicationName").toString();
		}catch(Exception e) {}
		
		try {
			description = requestBody.get("description").toString();
		}catch(Exception e) {}
		
		ApplicationModel model = applicationService.findById(applicationId);
		if(model==null) {
			return CommonUtil.createResult(401, "Application not found", null);
		}
		
		if(applicationName!=null && applicationName.trim().isEmpty()==false) {
			model.setApplicationName(applicationName);
		}
		if(description!=null) {
			model.setDescription(description);
		}
		try {
			applicationService.save(model);
			return CommonUtil.createResult(200, "Ok", model);
		} catch (Exception e) {
			return CommonUtil.createResult(409, "Duplicate entry applicationName", null);
		}
		
	}
	
	@DeleteMapping("/delete")
	Map<String,Object> delete(@RequestBody Map<String, Object> requestBody) {
		logger.info("/user/delete");
		String applicationId;
		try {
			applicationId = requestBody.get("applicationId").toString().trim();
			ApplicationModel model = applicationService.findById(applicationId);
			if(model!=null) {
				userApplicationService.delelteByApplicationModel(model);
				applicationService.delete(model);
				return CommonUtil.createResult(200, "Ok", null);
			}else {
				return CommonUtil.createResult(400,"ApplicationId not existed", null);
			}
			
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, e.toString(), null);
		}
	}


}

package ngn.otp.otp_core.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ngn.otp.otp_core.models.QRCodeLoginModel;
import ngn.otp.otp_core.services.QRCodeLoginService;
import ngn.otp.otp_core.services.UserService;
import ngn.otp.otp_core.utils.CommonUtil;
import ngn.otp.otp_core.utils.PropUtil;

@RestController
@RequestMapping(path = "/qrcode")
public class QRCodeLoginController {
	Logger logger = LoggerFactory.getLogger(QRCodeLoginController.class);
	private UserService userService;
	private QRCodeLoginService qrService;
	private PropUtil prop ;
	
	public QRCodeLoginController(UserService userService, QRCodeLoginService qrService, PropUtil prop) {
		super();
		this.userService = userService;
		this.qrService = qrService;
		this.prop = prop;
	}
	
	
	@PostMapping("/requestWithKey")
	Map<String, Object> requestWithKey(@RequestBody Map<String, Object> requestBody){
		logger.info("requestWithKey");
		String key="";
		try {
			key= requestBody.get("key").toString();
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "key is required", null);
		}
		
		if(this.qrService.insert(key)==false) {
			return CommonUtil.createResult(400, "key is required or duplicate", null);
		}
		
		QRCodeLoginModel model = waitingForConfirmKey(key);
		if(model==null) {
			this.qrService.delete(key);
			return CommonUtil.createResult(400, "no one confirm this key: "+key, null);
		}
		this.qrService.delete(key);
		return CommonUtil.createResult(200, "Ok", model);
	}
	
	@PostMapping("/confirmKey")
	Map<String, Object> confirmKey(@RequestBody Map<String, Object> requestBody){
		logger.info("confirmKey");
		String key="";
		String userId="";
		try {
			key= requestBody.get("key").toString();
			userId=requestBody.get("userId").toString();
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "key and userId are required", null);
		}
		
		QRCodeLoginModel model = this.qrService.get(key);
		if(model==null) {
			return CommonUtil.createResult(400, "no key found", key);
		}
		
		model.setUserId(userId);
		this.qrService.update(model);
		
		return CommonUtil.createResult(200, "Ok", model);
	}
	
	
	
	private QRCodeLoginModel waitingForConfirmKey(String key) {
		int waitingTime = 30;
		try {
			waitingTime = Integer.valueOf( prop.get("qrcode.waitingTime"));
			logger.info("waiting time: "+waitingTime);
		}catch(Exception e) {
			
		}
		int count=0;
		for(;;) {
			QRCodeLoginModel model = this.qrService.get(key);
			if(model!=null) {
				logger.info(model.getKey()+" "+model.getUserId());
				if(model.getUserId()!=null && model.getUserId().isEmpty()==false) {
					return model;
				}
			
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e.toString());
				return null;
			}
			
			count++;
			if(count==waitingTime) {
				break;
			}
			
		}
		return null;
		
	}
	@PostMapping("/requestWithUserId")
	Map<String, Object> requestWithUserId(@RequestBody Map<String, Object> requestBody){
		logger.info("requestWithUserId");
		String key="";
		String userId="";
		try {
			key= requestBody.get("key").toString();
			userId=requestBody.get("userId").toString();
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "key and userId is required", null);
		}
		
		this.qrService.insert(key,userId);
		return CommonUtil.createResult(200, "Ok", null);
	}
	
	
	

}

package ngn.otp.otp_core.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
	
	@GetMapping("/browser-info")
    public String getBrowserInfo(HttpServletRequest request) {
        // Extract the User-Agent header
        String userAgent = request.getHeader("User-Agent");

        // You can return the User-Agent string directly
        return "User-Agent: " + userAgent;
    }
	
	@PostMapping("/login")
	Map<String,Object> login(HttpServletRequest request){
		int waitingTime = 30;
		try {
			waitingTime = Integer.valueOf( prop.get("qrcode.waitingTime"));
			logger.info("waiting time: "+waitingTime);
		}catch(Exception e) {
			
		}
		
		// Create a session if it doesn't exist, or get the existing one
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(waitingTime);
        

        // Get the session ID
        String sessionId = session.getId();
        
		return CommonUtil.createResult(200, "Ok", sessionId);
		
		
	}
	
	@PostMapping("/requestWithKey")
	Map<String, Object> requestWithKey(HttpServletRequest request,@RequestBody Map<String, Object> requestBody){
		logger.info("requestWithKey");
		String key="";
		String info="";
		try {
			key= requestBody.get("key").toString();
			
		}catch(Exception e) {
			return CommonUtil.createResult(400, "key is required", null);
		}
		
		try {
			info= requestBody.get("info").toString();
		}catch(Exception e) {
			info = request.getHeader("User-Agent");
		}
		
		QRCodeLoginModel model = new QRCodeLoginModel();
		model.setKey(key);
		model.setInfo(info);
		
//		if(this.qrService.insert(key)==false) {
//			return CommonUtil.createResult(400, "key is required or duplicate", null);
//		}
		
		if(this.qrService.insert(model)==false) {
			return CommonUtil.createResult(400, "key is required or duplicate", null);
		}
		
		model = waitingForConfirmKey(key);
		if(model==null) {
			this.qrService.delete(key);
			return CommonUtil.createResult(400, "no one confirm this key: "+key, null);
		}
		
//		this.qrService.delete(key);
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
		
		QRCodeLoginModel model = this.qrService.get(key);
		if(model==null) {
			return CommonUtil.createResult(400, "no key found", key);
		}
		
		if(model.getUserId().equals(userId)==false) {
			return CommonUtil.createResult(400, "invalid userId", userId);
		}
		
		model = waitingForConfirmUserId(key,userId);
		if(model==null) {
			this.qrService.delete(key);
			return CommonUtil.createResult(400, "no one confirm this userId: "+userId, null);
		}
		this.qrService.delete(key);
		return CommonUtil.createResult(200, "Ok", model);
	}


	@PostMapping("/confirmUserId")
	Map<String, Object> confirmUserId(@RequestBody Map<String, Object> requestBody){
		logger.info("confirmUserId");
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
		
		if(model.getUserId()==null || model.getUserId().trim().isEmpty()) {
			return CommonUtil.createResult(400, "no userId found", userId);
		}
		if(model.getUserId().equals(userId)==false) {
			return CommonUtil.createResult(400, "invalid userId", userId);
		}
		model.setConfirmed(true);
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
	
	private QRCodeLoginModel waitingForConfirmUserId(String key, String userId) {
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
				if(model.getUserId()!=null && model.getUserId().isEmpty()==false && model.isConfirmed()) {
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
	
	
	

}

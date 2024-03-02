package ngn.otp.otp_core.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ngn.otp.otp_core.utils.CommonUtil;



@RestController
@RequestMapping(path = "/user")
public class UserController {
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/sayHello")
	Map<String, Object> sayHello(){
		logger.info("/user/sayHello");
		return CommonUtil.createResult(200, "Success", "hello, this is user controller");
	}

}

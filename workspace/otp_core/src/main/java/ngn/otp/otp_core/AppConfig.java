package ngn.otp.otp_core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ngn.otp.otp_core.utils.PropUtil;

@Configuration
@ComponentScan
public class AppConfig {
	private Logger logger = LoggerFactory.getLogger(AppConfig.class);
	@Bean
	PropUtil propUtil() {
		return new PropUtil();
	}
}

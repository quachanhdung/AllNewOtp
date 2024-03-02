package ngn.otp.otp_core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ngn.otp.otp_core.utils.PropUtil;

@Configuration
public class AppConfig {
	@Bean
	PropUtil propUtil() {
		return new PropUtil();
	}
}

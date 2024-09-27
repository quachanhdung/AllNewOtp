package ngn.otp.otpadmin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PropUtil implements EnvironmentAware{
	private Logger logger = LoggerFactory.getLogger(PropUtil.class);
	private  Environment props;
	public PropUtil() {
		logger.info("PropUtil start ...");
	}
	public  String get(String property) {
		String value="";
		try {
			value= props.getProperty(property);
			if(value==null) {
				return "";
			}else {
				return value;
			}
		} catch (Exception e) {
			return "";
		}	
	}
	@Override
	public void setEnvironment(Environment environment) {
		this.props = environment;
		
	}
}

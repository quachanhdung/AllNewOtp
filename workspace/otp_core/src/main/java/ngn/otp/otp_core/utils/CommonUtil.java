package ngn.otp.otp_core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	public static Map<String, Object> createResult(int status, String message, Object data){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("data", data);
		return map;
	}
	
	public static boolean isValidEmailAddress(String emailAddress) {
		String emailPattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
	}
	
	public static boolean isValidPhoneNumber(String phoneNumber) {
		 String pattern = "^\\d{10}$";
	        // Create a Pattern object
	        Pattern r = Pattern.compile(pattern);
	        // Create a Matcher object
	        Matcher m = r.matcher(phoneNumber);
	      return m.matches();
    }

}

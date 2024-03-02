package ngn.otp.otp_core.utils;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
	public static Map<String, Object> createResult(int status, String message, Object data){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("status", status);
		map.put("message", message);
		map.put("data", data);
		return map;
	}

}

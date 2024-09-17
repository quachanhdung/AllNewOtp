package ngn.otp.otp_core.security;

import java.util.Date;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import jakarta.servlet.http.HttpServletRequest;
import ngn.otp.otp_core.ApplicationContextProvider;
import ngn.otp.otp_core.utils.PropUtil;

public class AuthenticationService {

	private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

	public static Authentication getAuthentication(HttpServletRequest request) {
		try {
			PropUtil prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);

			String AUTH_TOKEN = prop.get("apikey");
			String key = prop.get("apikey.key");
			String iv = prop.get("apikey.iv");
			long duration = Long.parseLong(prop.get("apikey.duration"));

			String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);


			apiKey=ApiKeyDecrypt.decrypt(apiKey, key, iv);


			System.out.println("AUTH_TOKEN: "+AUTH_TOKEN);
			System.out.println("apiKey: "+apiKey);

			if (apiKey == null || !apiKey.contains(AUTH_TOKEN)) {
				throw new BadCredentialsException("Invalid API Key");

			}
			
			//check time of apiKey
//			if (apiKey.contains(AUTH_TOKEN)) {
//				long time=Long.parseLong(apiKey.substring(AUTH_TOKEN.length()))/1000;
//				System.out.println("time: "+time);
//				long curTime = new Date().getTime()/1000;
//				long diff = (curTime-time);
//				System.out.println("diff: "+diff);
//				if(diff>duration) {
//					throw new BadCredentialsException("Invalid API Key");
//				}
//			}
			System.out.println("ok nha");
			return new ApiKeyAuthentication(AUTH_TOKEN, AuthorityUtils.NO_AUTHORITIES);
		}catch(Exception e) {
			
			throw new BadCredentialsException("Invalid API Key");
		}


	}
}

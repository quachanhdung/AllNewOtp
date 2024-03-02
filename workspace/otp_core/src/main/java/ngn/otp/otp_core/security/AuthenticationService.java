package ngn.otp.otp_core.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import jakarta.servlet.http.HttpServletRequest;
import ngn.otp.otp_core.ApplicationContextProvider;
import ngn.otp.otp_core.utils.PropUtil;

public class AuthenticationService {

    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    public static Authentication getAuthentication(HttpServletRequest request) {
    
    	PropUtil prop = ApplicationContextProvider.getContext().getBean(PropUtil.class);
    	String AUTH_TOKEN = prop.get("apikey");
    	System.out.println("AUTH_TOKEN: "+AUTH_TOKEN);
    	
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(AUTH_TOKEN)) {
            throw new BadCredentialsException("Invalid API Key");
        	
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}

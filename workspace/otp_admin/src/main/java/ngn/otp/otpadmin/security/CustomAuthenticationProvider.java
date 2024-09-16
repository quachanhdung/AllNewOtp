package ngn.otp.otpadmin.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import ngn.otp.otpadmin.commons.ServiceResponseBody;
import ngn.otp.otpadmin.models.UserModel;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private AuthenService service;
	
	public CustomAuthenticationProvider(AuthenService service) {
		this.service=service;
	}
	
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
     
      String username = authentication.getName();
      String password = authentication.getCredentials().toString();
//      UserModel model = null;
//      try {
//    	  ServiceResponseBody response = service.authen(username, password);
//    	  if(response.getStatus()==0) {
//    		  Gson gson = new Gson();
//    		  String jsonString = gson.toJson(response.getData());
//    		  model = gson.fromJson(jsonString, UserModel.class);
//    		  return  new UsernamePasswordAuthenticationToken(model, password, Collections.emptyList());
//
//    	  }else {
//    		  throw new BadCredentialsException("Authentication failed");
//    	  }
//      } catch (IOException e) {
//
//    	  e.printStackTrace();
//    	  throw new BadCredentialsException("Authentication failed");
//      }
      
      UserModel model = new UserModel();
      model.setFullName("Quach Anh Dung");
      return  new UsernamePasswordAuthenticationToken(model, password, Collections.emptyList());
      
    }

    @Override
    public boolean supports(Class<?>aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
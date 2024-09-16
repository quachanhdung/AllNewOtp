package ngn.otp.otpadmin.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;

import ngn.otp.otpadmin.models.UserModel;


@Component
public class SecurityService  {

    private static final String LOGOUT_SUCCESS_URL = "/";
    
 

    public Optional<UserModel> getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        System.out.println(principal.toString());

        if (principal instanceof UserModel) {
          UserModel userModel = (UserModel) principal;
//          SessionUtil.setUser(userModel);	
//          SessionUtil.setToken(userModel.getToken());
//          SessionUtil.setAPI_URL(env.getProperty("apiUrl"));
//          SessionUtil.setMQTT_URL(env.getProperty("mqttUrl"));
//          SessionUtil.setMQTT_PORT(env.getProperty("mqttPort"));
//          SessionUtil.setPACKAGE_NAME(env.getProperty("system.package.name"));
//          
          return Optional.of(userModel);
        }

        return Optional.ofNullable(null);
    }

    public void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }
}
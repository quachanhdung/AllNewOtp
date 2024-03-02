package ngn.otp.otp_core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ApplicationContextProvider {

    private static ApplicationContext context;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    private void init() {
        context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}

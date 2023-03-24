package ibf2022.batch2.ssf.frontcontroller.services;

import org.springframework.stereotype.Service;

@Service
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LoginAttemptService {

    private int attempts = 0;

    public void incrementAttempts() {
        attempts++;
    }

    public int getAttempts() {
        return attempts;
    }

    public void resetAttempts() {
        attempts = 0;
    }
}

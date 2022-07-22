package tn.esprit.wellness.service;

import org.springframework.security.core.context.SecurityContextHolder;
import tn.esprit.wellness.security.services.UserDetailsImpl;

public class UserSessionService implements IUserSessionService {
    @Override
    public UserDetailsImpl getUser() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

package tn.esprit.wellness.service;

import org.springframework.security.core.userdetails.UserDetails;
import tn.esprit.wellness.security.services.UserDetailsImpl;

public interface IUserSessionService {
    UserDetailsImpl getUser();
}

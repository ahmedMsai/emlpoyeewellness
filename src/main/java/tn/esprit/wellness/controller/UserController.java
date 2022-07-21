package tn.esprit.wellness.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.entity.Article;
import tn.esprit.wellness.entity.User;
import tn.esprit.wellness.payload.response.UserInfoResponse;
import tn.esprit.wellness.security.services.UserDetailsImpl;
import tn.esprit.wellness.service.IUserService;
import tn.esprit.wellness.service.UserService;

@RestController
@RequestMapping(value = "/api/auth/user")
public class UserController  {

	@Autowired
    IUserService userService;
	
	@GetMapping(value = "/profile")
	public ResponseEntity getProfile(){
		UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
		
		return ResponseEntity.ok()
        .body(new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
	}
}

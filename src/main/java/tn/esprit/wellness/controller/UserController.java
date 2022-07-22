package tn.esprit.wellness.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.wellness.entity.Article;
import tn.esprit.wellness.payload.response.UserInfoResponse;
import tn.esprit.wellness.security.services.UserDetailsImpl;

@RestController
@RequestMapping(value = "/api/auth/user")
public class UserController {

	@GetMapping(value = "/profile")
	public ResponseEntity<?> getProfile(){
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

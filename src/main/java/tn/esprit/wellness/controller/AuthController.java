package tn.esprit.wellness.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import tn.esprit.wellness.builder.ScopeBuilder;
import tn.esprit.wellness.entity.ERole;
import tn.esprit.wellness.entity.Role;
import tn.esprit.wellness.entity.User;
import tn.esprit.wellness.payload.request.LoginRequest;
import tn.esprit.wellness.payload.request.SignupRequest;
import tn.esprit.wellness.payload.response.MessageResponse;
import tn.esprit.wellness.payload.response.UserInfoResponse;
import tn.esprit.wellness.pojo.AccessToken;
import tn.esprit.wellness.repository.RoleRepository;
import tn.esprit.wellness.repository.UserRepository;
import tn.esprit.wellness.security.jwt.JwtUtils;
import tn.esprit.wellness.security.services.UserDetailsImpl;
import tn.esprit.wellness.service.LinkedInOAuthService;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static tn.esprit.wellness.Constants.*;
import static tn.esprit.wellness.util.Constants.REQUEST_TOKEN_URL;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    private Logger logger = Logger.getLogger(AuthController.class.getName());

    private Properties prop = new Properties();
    private String propFileName = "config.properties";
    public static String token = null;
    public String refresh_token = null;
    public LinkedInOAuthService service;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "moderator":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @RequestMapping(value = "/login")
    public Object oauth(@RequestParam(name = "code", required = false) final String code) throws Exception {

        loadProperty();

        service = new LinkedInOAuthService.LinkedInOAuthServiceBuilder()
                .apiKey(prop.getProperty("clientId"))
                .apiSecret(prop.getProperty("clientSecret"))
                .defaultScope(new ScopeBuilder(prop.getProperty("scope").split(",")).build())
                .callback(prop.getProperty("redirectUri"))
                .build();

        final String secretState = "secret" + new Random().nextInt(999_999);
        final String authorizationUrl = service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();

        RedirectView redirectView = new RedirectView();

        if (code != null && !code.isEmpty()) {

            logger.log(Level.INFO, "Authorization code not empty, trying to generate a 3-legged OAuth token.");

            final AccessToken[] accessToken = {
                    new AccessToken()
            };
            HttpEntity request = service.getAccessToken3Legged(code);
            String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
            accessToken[0] = service.convertJsonTokenToPojo(response);

            prop.setProperty("token", accessToken[0].getAccessToken());
            token = accessToken[0].getAccessToken();
            refresh_token = accessToken[0].getRefreshToken();
            logger.log(Level.INFO, "Generated Access token and Refresh Token.");
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
            headers.set("Authorization", "Bearer " + token);
            String userEmail = new JSONObject(restTemplate.exchange(EMAIL_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody())
                    .getJSONArray("elements").getJSONObject(0).getJSONObject("handle~").get("emailAddress").toString();
            String userImgUrl = new JSONObject(restTemplate.exchange(PROFILE_IMG_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody())
                    .getJSONObject("profilePicture").getJSONObject("displayImage~").getJSONArray("elements").getJSONObject(0).getJSONArray("identifiers")
                    .getJSONObject(0).get("identifier").toString();

            String lastName = new JSONObject(getprofile()).get("localizedLastName").toString();
            String firstName = new JSONObject(getprofile()).get("localizedFirstName").toString();
            String password = new JSONObject(getprofile()).get("id").toString();

            if (!userRepository.existsByEmail(userEmail)) {
                SignupRequest signupRequest = new SignupRequest();
                signupRequest.setEmail(userEmail);
                signupRequest.setUsername(generateUsername(userEmail));
                signupRequest.setPassword(password);
                registerUser(signupRequest);
                User user = userRepository.findByEmail(userEmail).get();
                user.setImgUrl(userImgUrl);
                user.setLastname(lastName);
                user.setName(firstName);
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(user.getUsername());
                loginRequest.setPassword(password);
                return authenticateUser(loginRequest);

            } else {
                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setUsername(userRepository.findByEmail(userEmail).get().getUsername());
                loginRequest.setPassword(password);
                return authenticateUser(loginRequest);
            }
        } else {
            redirectView.setUrl(authorizationUrl);
        }
        return redirectView;
    }


    private void loadProperty() throws IOException {
        InputStream inputStream = AuthController.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public String getprofile() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
        return restTemplate.exchange(LI_ME_ENDPOINT + token, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody();
    }

    public String generateUsername(String email) {
        String probableUsername = email.substring(0, email.indexOf("@"));
        for (int i = 0; userRepository.existsByUsername(probableUsername); i++) {
            probableUsername = probableUsername + i;
        }
        return probableUsername;
    }

}

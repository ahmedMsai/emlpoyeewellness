package tn.esprit.wellness.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import tn.esprit.wellness.builder.ScopeBuilder;
import tn.esprit.wellness.pojo.AccessToken;
import tn.esprit.wellness.service.LinkedInOAuthService;
import org.json.JSONObject;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static tn.esprit.wellness.Constants.*;
import static tn.esprit.wellness.util.Constants.REQUEST_TOKEN_URL;
import static tn.esprit.wellness.util.Constants.TOKEN_INTROSPECTION_URL;

@RequestMapping("/api/linkedin")
@RestController
public final class LinkedInOAuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }


    private Properties prop = new Properties();
    private String propFileName = "config.properties";
    public static String token = null;
    public String refresh_token = null;
    public LinkedInOAuthService service;

    private Logger logger = Logger.getLogger(LinkedInOAuthController.class.getName());

    @RequestMapping(value = "/login")
    public RedirectView oauth(@RequestParam(name = "code", required = false) final String code) throws Exception {

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
            headers.set("Authorization", "Bearer "+token);
            String userEmail = new JSONObject(restTemplate.exchange( EMAIL_ENDPOINT, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody())
                    .getJSONArray("elements").getJSONObject(0).getJSONObject("handle~").get("emailAddress").toString();
            String UserImgUrl = new JSONObject(restTemplate.exchange(PROFILE_IMG_ENDPOINT,HttpMethod.GET,new HttpEntity<>(headers), String.class).getBody())
                    .getJSONObject("profilePicture").getJSONObject("displayImage~").getJSONArray("elements").getJSONObject(0).getJSONArray("identifiers")
                    .getJSONObject(0).get("identifier").toString();


            redirectView.setUrl(prop.getProperty("client_url"));
        } else {
            redirectView.setUrl(authorizationUrl);
        }
        return redirectView;
    }

    @RequestMapping(value = "/tokenIntrospection")
    public String token_introspection() throws Exception {
        if (service != null) {
            HttpEntity request = service.introspectToken(token);
            String response = restTemplate.postForObject(TOKEN_INTROSPECTION_URL, request, String.class);
            logger.log(Level.INFO, "Token introspected. Details are {0}", response);

            return response;
        } else {
            return TOKEN_INTROSPECTION_ERROR_MESSAGE;
        }
    }


    @RequestMapping(value = "/refreshToken")
    public String refresh_token() throws IOException {
        HttpEntity request = service.getAccessTokenFromRefreshToken(refresh_token);
        String response = restTemplate.postForObject(REQUEST_TOKEN_URL, request, String.class);
        logger.log(Level.INFO, "Used Refresh Token to generate a new access token successfully.");
        return response;
    }

    @RequestMapping(value = "/profile")
    public String profile() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, USER_AGENT_OAUTH_VALUE);
       return restTemplate.exchange(LI_ME_ENDPOINT + token, HttpMethod.GET, new HttpEntity<>(headers), String.class).getBody();
    }

    private void loadProperty() throws IOException {
        InputStream inputStream = LinkedInOAuthController.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }
}

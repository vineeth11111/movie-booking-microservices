package com.booking.auth.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class LoginController {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping(value = { "/login" })
	public ResponseEntity<Map<String, String>> getToken(OAuth2AuthenticationToken authentication,HttpServletResponse response) throws IOException {
		System.out.println("i am in get Token Method");
		String subjectId = authentication.getName();
		String accessToken = null;

		System.out.println("Logged in User is " + subjectId);

		OAuth2AuthorizedClient authorizedClient = authorizedClientService
				.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), subjectId);

		accessToken = authorizedClient.getAccessToken().getTokenValue();
		System.out.println("access Token " + accessToken);

		OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("id Token " + user.getIdToken().getTokenValue());

		Map<String, String> usersMap = new HashMap<>();
		usersMap.put("access_token", accessToken);
		usersMap.put("refresh_token", authorizedClient.getRefreshToken().getTokenValue());
		usersMap.put("id_token", user.getIdToken().getTokenValue());
		usersMap.put("token_type", authorizedClient.getAccessToken().getTokenType().getValue());
		usersMap.put("token_expiry", authorizedClient.getAccessToken().getExpiresAt().toString());

		/*
		 * JSONObject jsonObject = new JSONObject(usersMap);
		 * System.out.println("json Object is " + jsonObject.toString());
		 * 
		 * Cookie cookie = new Cookie("acmaCk",accessToken); cookie.setPath("/");
		 * cookie.setDomain("localhost");
		 * 
		 * response.addCookie(cookie);
		 * 
		 * response.sendRedirect("http://localhost:9090/dashboard");
		 */

		return new ResponseEntity<Map<String, String>>(usersMap, HttpStatus.OK);

	}

}

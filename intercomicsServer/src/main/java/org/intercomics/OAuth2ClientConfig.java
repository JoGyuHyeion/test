package org.intercomics;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import org.intercomics.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;

import web.configuration.security.OAuth2SuccessHandler;
import web.configuration.security.UserTokenServices;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
	@Autowired
	OAuth2ClientContext oauth2ClientContext;

	@Autowired
	UserService userService;

	@Value("${naver.client.redirectUri}")
	private String naverRedirectUri;

	@Value("${naver.resource.userInfoUri}")
	private String naverUserInfoUri;

	@Bean
	@ConfigurationProperties("naver.client")
	AuthorizationCodeResourceDetails naver() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		// 스프링 사이트에 의하면 다른 필터보다 우선순위를 올리기위해 -100을 주었다고 나옵니다.
		registration.setOrder(-100);
		return registration;
	}

	@Bean("sso.filter")
	Filter ssoFilter() {
		List<Filter> filters = new ArrayList<>();

		// 네이버
		OAuth2ClientAuthenticationProcessingFilter naver = new OAuth2ClientAuthenticationProcessingFilter(
				naverRedirectUri);
		naver.setRestTemplate(new OAuth2RestTemplate(naver(), oauth2ClientContext));
		naver.setTokenServices(new UserTokenServices(naverUserInfoUri, naver().getClientId()));
		naver.setAuthenticationSuccessHandler(new OAuth2SuccessHandler("naver", userService));
		filters.add(naver);

		CompositeFilter filter = new CompositeFilter();
		filter.setFilters(filters);
		return filter;
	}
}
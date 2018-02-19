package org.intercomics;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
class JwtOAuth2AuthorizationServerConfiguration extends OAuth2AuthorizationServerConfiguration {

	private final JwtAccessTokenConverter jwtAccessTokenConverter;
	private final ClientDetailsService clientDetailsService;

	public JwtOAuth2AuthorizationServerConfiguration(BaseClientDetails details,
			AuthenticationManager authenticationManager, ObjectProvider<TokenStore> tokenStoreProvider,
			AuthorizationServerProperties properties, JwtAccessTokenConverter jwtAccessTokenConverter,
			ClientDetailsService clientDetailsService) {
		super(details, authenticationManager, tokenStoreProvider, properties);
		this.jwtAccessTokenConverter = jwtAccessTokenConverter;
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		super.configure(endpoints);
		endpoints.accessTokenConverter(jwtAccessTokenConverter);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}
}
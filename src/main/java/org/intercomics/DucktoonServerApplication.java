package org.intercomics;

import javax.servlet.Filter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableResourceServer
@EnableAuthorizationServer
@SpringBootApplication
@MapperScan(value= {"org.intercomics.mapper"})
@PropertySource(value = { "classpath:application.properties",  "classpath:${jdbc.config}","classpath:${jwt.config}" })
public class DucktoonServerApplication {
	
	@Autowired
	ApplicationContext context;
	
	@Bean
	public ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
		return new ResourceServerConfigurerAdapter() {
			
			
			@Override
			public void configure(HttpSecurity http) throws Exception {
				http.headers().frameOptions().disable();
				http.authorizeRequests()
				.antMatchers("/test").access("#oauth2.hasScope('read')")
				.anyRequest().permitAll();
				
				http.formLogin()
				// 로그인 처리 페이지 : 지난 강의에선 /login 이였지만
				// 이번엔 직접 작성한 뷰를 보여줄 것이기 때문에 사용자에게
				// login 이라는 화면을 보여주는게 더 깔끔할 것 같아서 교체함!
				.loginPage("/login")
				// 로그인 페이지
				.usernameParameter("userName")
				.passwordParameter("password")
				.loginProcessingUrl("/loginProcessing")
				.defaultSuccessUrl("/home")

				// 로그인 실패 페이지
				.failureUrl("/login?error=false");

				http.logout()
				// /logout 을 호출할 경우 로그아웃
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				// 로그아웃이 성공했을 경우 이동할 페이지
				.logoutSuccessUrl("/");
				
				http.addFilterBefore((Filter)context.getBean("sso.filter"), BasicAuthenticationFilter.class);
				
			}
		};
	}

	
	public static void main(String[] args) {
		SpringApplication.run(DucktoonServerApplication.class, args);
	}
}

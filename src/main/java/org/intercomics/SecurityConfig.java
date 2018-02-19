package org.intercomics;

//import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	ApplicationContext context;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 메인페이지 : css나 js 같은것들도 여기에 포함시켜준다.
		web.ignoring().antMatchers("/");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	/*	
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
			.antMatchers("/login", "/create", "/createProcessing","/nlogin","/sign-in/naver").permitAll()
			.antMatchers("/*").authenticated();

		http.formLogin()
				// 로그인 처리 페이지 : 지난 강의에선 /login 이였지만
				// 이번엔 직접 작성한 뷰를 보여줄 것이기 때문에 사용자에게
				// login 이라는 화면을 보여주는게 더 깔끔할 것 같아서 교체함!
				.loginPage("/login")
				// 로그인 페이지
				.usernameParameter("userName")
				.passwordParameter("password")
				.loginProcessingUrl("/loginProcessing")
				.defaultSuccessUrl("/mypage")

				// 로그인 실패 페이지
				.failureUrl("/login?error=false");

		http.logout()
				// /logout 을 호출할 경우 로그아웃
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				// 로그아웃이 성공했을 경우 이동할 페이지
				.logoutSuccessUrl("/");
		http.addFilterBefore((Filter)context.getBean("sso.filter"), BasicAuthenticationFilter.class);
		
		*/
		
	}

	@Configuration
	public static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
		@Autowired
		UserDetailsService userDetailsService;

		@Bean
		PasswordEncoder passwordEncoder() {
			// 인증에 기본 스프링 해시를 사용하겠습니다.
			return new BCryptPasswordEncoder();
		}

		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
	}
}
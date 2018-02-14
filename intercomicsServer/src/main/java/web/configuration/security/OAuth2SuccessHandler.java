package web.configuration.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.intercomics.service.UserService;
import org.intercomics.user.LoginUserDetails;
import org.intercomics.user.UserVO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler
{
	final String type;
	final UserService userService;
	
	public OAuth2SuccessHandler(String type, UserService userService)
	{
		this.type = type;	
		this.userService = userService;
	}
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {
		try {
			// 연결되어 있는 계정이 있는지 확인합니다.
						
			UserVO user = userService.getUser(auth.getName());
			if (user != null)
			{
				// 기존 인증을 바꿉니다.
				// 일반 로그인과 동일한 방식으로 로그인한 것으로 간주하여 처리합니다.
				// 기존 인증이 날아가기 때문에 OAUTH ROLE은 증발하며, USER ROLE 이 적용됩니다.
				SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken
				(
					new LoginUserDetails(user), null, LoginUserDetails.getAuthorities(user.getRoles()))
				);
				res.sendRedirect("/");
			}
			// 연결된 계정이 없는경우
			else
			{
				// 회원가입 페이지로 보냅니다.
				// ROLE 은 OAUTH 상태입니다.
				//res.sendRedirect("/sign-up/oauth");
				res.sendRedirect("/create");
				
				// 특별히 추가정보를 받아서 가입해야할 일이없다면,
				// 즉석으로 계정을 생성한 후 성공처리 해준다면 사용자 입장에서 좋을 것 같습니다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
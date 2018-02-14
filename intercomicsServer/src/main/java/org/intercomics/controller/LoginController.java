package org.intercomics.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import org.apache.ibatis.annotations.Param;
import org.intercomics.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@Value("${naver.client.clientId}")
	private String naverClientId;
	@Value("${naver.client.clientSecret}")
	private String naverClientSecret;
	@Value("${naver.client.redirectUri}")
	private String naverRedirectUri;
	@Value("${naver.resource.apiURL}")
	private String naverApiURL;



	private String getProfile(String accessToken) {
		String token = accessToken;// 네이버 로그인 접근 토큰;
		String header = "Bearer " + token; // Bearer 다음에 공백 추가
		try {
			String apiURL = "https://openapi.naver.com/v1/nid/me";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			return response.toString();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private String getAccessToken(String code, String state) throws UnsupportedEncodingException {
		String redirectURI = URLEncoder.encode(naverRedirectUri, "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + naverClientId;
		apiURL += "&client_secret=" + naverClientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				return res.toString();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@RequestMapping(value = "/sign-in/naver", method = RequestMethod.GET)
	public String signnaver(@RequestParam("code") String code, @RequestParam("state") String state, Model model)
			throws UnsupportedEncodingException {

		String accessToken = getAccessToken(code, state);

		JSONObject json = new JSONObject(accessToken);

		String profile = getProfile(json.getString("access_token"));

		model.addAttribute("accessToken", accessToken);
		model.addAttribute("profile", profile);

		return "/sign-in/naver";
	}

	@RequestMapping("/test")
	public void test(Model model) {
		model.addAttribute("msg", "Hello Worold");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) throws Exception {

		String clientId = naverClientId;
		String redirectURI = URLEncoder.encode(naverRedirectUri, "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = naverApiURL;
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state;
		model.addAttribute("apiURL", apiURL);
		return "login";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html")
	public String root(Model model) throws UnsupportedEncodingException {
		String clientId = naverClientId;
		String redirectURI = URLEncoder.encode(naverRedirectUri, "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = naverApiURL;
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state;
		model.addAttribute("apiURL", apiURL);
		return "login";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET, produces = "text/html")
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() {
		return "create";
	}

	@Autowired
	UserService userService;

	@RequestMapping(value = "/createProcessing", method = RequestMethod.POST)
	public String createProcessing(@Param("userName") String userName, @Param("password") String password)
			throws Exception {

		System.out.println("jo : " + userName + password);
		if (userService.create(userName, password)) {
			return "redirect:/login";
		} else {
			return "redirect:/create?error=false";
		}
	}

}

package org.intercomics.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.intercomics.domain.LoginDTO;
import org.intercomics.domain.OauthAccessTokenVO;
import org.intercomics.mapper.ProfileRepository;
import org.intercomics.mapper.TokenRepository;
import org.intercomics.service.UserService;
import org.intercomics.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Resource(name="OauthAccessTokenVO")
	private OauthAccessTokenVO tokenVO;
	

	@RequestMapping(value = "/jwt/token", method = RequestMethod.POST)
	public ResponseEntity<String> takeToekn(@RequestBody LoginDTO dto) {

		ResponseEntity<String> entity = null;

		try {
			UserVO vo = userService.getUser(dto.getUsername());
			if (vo == null) {
				userService.create(dto.getUsername(), dto.getPassword());
				tokenVO.setClient_id(dto.getClient_id());
				tokenVO.setToken(dto.getAccess_token());
				tokenVO.setUserId(dto.getUsername());
				tokenRepository.newToken(tokenVO);
				profileRepository.newProfile(dto);
			}

			// String client_id = "facebook_id";

			CloseableHttpClient client = HttpClients.createDefault();

			String url = "http://facebook_id:facebook_secret@127.0.0.1:8080/oauth/token";
			HttpPost post = new HttpPost(url);
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

			List<NameValuePair> params = new ArrayList<NameValuePair>(2);
			params.add(new BasicNameValuePair("grant_type", "password"));
			params.add(new BasicNameValuePair("client_id", "facebook_id"));
			params.add(new BasicNameValuePair("scope", "read"));
			params.add(new BasicNameValuePair("username", dto.getUsername()));
			params.add(new BasicNameValuePair("password", dto.getPassword()));
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(post);

			entity = new ResponseEntity<String>(EntityUtils.toString(response.getEntity()), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	/*
	 * @RequestMapping(value = "/jwt/token2", method = RequestMethod.POST) public
	 * ResponseEntity<String> takeToekn2(@RequestBody LoginDTO dto) {
	 * 
	 * ResponseEntity<String> entity = null;
	 * 
	 * try { UserVO vo = userService.getUser(dto.getUsername()); if(vo==null) {
	 * userService.create(dto.getUsername(), dto.getPassword()); } // String
	 * username = "hhw"; // String password = "1234"; String client_id =
	 * "facebook_id"; // String client_secret = "facebook_secret"; String url =
	 * "http://facebook_id:facebook_secret@127.0.0.1:8080/oauth/token"; String[]
	 * command = { "curl", url, "-d", "grant_type=password", "-d", "client_id=" +
	 * client_id, "-d", "scope=read", "-d", "username=" + dto.getUsername(), "-d",
	 * "password=" + dto.getPassword() }; ProcessBuilder process = new
	 * ProcessBuilder(command); Process p; p = process.start(); BufferedReader
	 * reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	 * StringBuilder builder = new StringBuilder(); String line = null; while ((line
	 * = reader.readLine()) != null) { builder.append(line);
	 * builder.append(System.getProperty("line.separator")); } entity = new
	 * ResponseEntity<String>(builder.toString(), HttpStatus.OK); } catch (Exception
	 * e) { e.printStackTrace(); entity = new
	 * ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST); } return
	 * entity; }
	 */
}

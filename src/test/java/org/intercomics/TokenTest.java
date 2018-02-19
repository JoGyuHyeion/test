package org.intercomics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenTest {
	@Test
	public void context() {
		 String str1 = " 3 4";
	        String[] words = str1.split("\\s");
	         
	        for (String wo : words ){
	            System.out.println(wo);
	        }



	}

//	 @Test
	public void toekn() {
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTYwNjMzNzMsInVzZXJfbmFtZSI6ImhodyIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJmYmRlN2VmNS0wZTNlLTQwMTgtOGZkMS0xMmFkMmRlODcyZDIiLCJjbGllbnRfaWQiOiJmYWNlYm9va19pZCIsInNjb3BlIjpbInJlYWQiXX0.QkH9xyecdmWLqV1S_ZceDBNptdgS5n_MdfFL_p6Jk-s";
		Jwt jwt = JwtHelper.decode(token);
//		System.out.println(jwt.getClaims());
		JSONObject obj=new JSONObject(jwt.getClaims());
		System.out.println(obj.get("user_name"));
		

//		System.out.println(jwt.toString());
	}

	// @Test
	public void takeToekn() {

		String url = "http://facebook_id:facebook_secret@localhost:8080/oauth/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "password");
		map.add("client_id", "facebook_id");
		map.add("scope", "read");
		map.add("username", "hhw");
		map.add("password", "1234");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		System.out.println(response.toString());

	}

	/* @Test
	public void test() {
		String username = "hhw";
		String password = "1234";
		String client_id = "facebook_id";
		String client_secret = "facebook_secret";
		String url = "http://facebook_id:facebook_secret@localhost:8080/oauth/token";
		String[] command = { "curl", url, "-d", "grant_type=password", "-d", "client_id=" + client_id, "-d",
				"scope=read", "-d", "username=" + username, "-d", "password=" + password };
		ProcessBuilder process = new ProcessBuilder(command);
		Process p;
		try {
			p = process.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.print(result);

		} catch (IOException e) {
			System.out.print("error");
			e.printStackTrace();
		}
	}
	*/

	// @Test
	public void test2() throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost post = new HttpPost("http://facebook_id:facebook_secret@localhost:8080/oauth/token");
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
		StringEntity data = new StringEntity(
				"grant_type=password&client_id=facebook_id&scope=read&username=hhw&password=1234");
		post.setEntity(data);

		HttpResponse response = client.execute(post);

		System.out.println(EntityUtils.toString(response.getEntity()));
	}

//	@Test
	public void test3() throws ClientProtocolException, IOException {

		CloseableHttpClient client = HttpClients.createDefault();

		String url = "http://facebook_id:facebook_secret@127.0.0.1:8080/oauth/token";
		HttpPost post = new HttpPost(url);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");

		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("grant_type", "password"));
		params.add(new BasicNameValuePair("client_id", "facebook_id"));
		params.add(new BasicNameValuePair("scope", "read"));
		params.add(new BasicNameValuePair("username", "hhw"));
		params.add(new BasicNameValuePair("password", "1234"));
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse response = client.execute(post);
		System.out.println(EntityUtils.toString(response.getEntity()));
	}

}

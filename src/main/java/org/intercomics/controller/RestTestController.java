package org.intercomics.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {

	@RequestMapping(value = "/getHeader", method = RequestMethod.GET)
	public ResponseEntity<String> testHeader2(HttpServletRequest request, HttpServletResponse response) {
		ResponseEntity<String> entity = null;
		try {
			System.out.println("getHeader : " + request.getHeader("x-token"));
			
			Jwt jwt = JwtHelper.decode(request.getHeader("x-token"));

			JSONObject obj=new JSONObject(jwt.getClaims());
//			System.out.println(jwt.getClaims());
			System.out.println(obj.get("user_name"));
			
			entity = new ResponseEntity<>(request.getHeader("x-token"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}


}

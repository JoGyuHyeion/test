<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
</head>
<body>
	<div>

		<h1>로그인을 해봅시다.!!</h1>

		<form action="/loginProcessing" method="post">
			<h2>로그인 폼</h2>

			<c:if test="${!empty param.error}">
				<div> 잘못된 아이디나 암호입니다. </div>
			</c:if>
			<input type="text" name="userName" placeholder="계정" required/> 
			<input type="password" name="password" placeholder="암호" required/> 
			<input type="submit" value="로그인" />
			<a href="/create">회원가입</a><br />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			
  			<a href="${apiURL}"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
		</form>

	</div>
</body>
</html>
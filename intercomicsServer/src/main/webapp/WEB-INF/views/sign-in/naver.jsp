<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>네이버로그인</title>
</head>
<body>
	<h1>네이버 Access Toekn</h1>
	${accessToken}
	<H1>네이버 포로필</H1>
	${profile}
</body>
</html>
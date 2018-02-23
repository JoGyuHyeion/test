<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
</head>
<body>
	<div>

		<form action="/createProcessing" method="post">
			<h2>회원가입</h2>

			<!-- 강의를 간결하게 끝내기 위함.. 원래는 가입실패 여부를 자세히 표시해야한다 -->
			<c:if test="${!empty param.error}">
				<div> 계정이 중복됩니다. </div>
			</c:if>

			<input type="text" name="userName" placeholder="계정" required />
			<input type="password" name="password" placeholder="암호" required />
			<input type="submit" value="회원가입" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		</form>

	</div>
</body>
</html>
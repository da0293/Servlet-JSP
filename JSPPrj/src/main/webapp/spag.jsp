<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- -------------------------------------------------------------- -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	pageContext.setAttribute("result", "hello");
%>
<body>
	${requestScope.result}<br>
	${result}<br>
	<!-- String num_=  request.getParameter("n");  -->
	${param.n}<br>
	<!-- header:   -->
	${header.accept}
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="net.board.db.*" %>

<%
	String id = (String)session.getAttribute("id");
	BoardBean board = (BoardBean)request.getAttribute("boarddata");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<script type="text/javascript">
	function modifyboard() {
		modifyform.submit();
	}
</script>
</head>

<body>
	<!-- 게시판 수정 -->
</body>
</html>
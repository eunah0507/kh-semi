<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.board.db.*" %>
<%
	String id=(String)session.getAttribute("id");
	BoardBean board=(BoardBean)request.getAttribute("boarddata");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
<script>
function replyboard(){
	boardform.submit();
}
</script>
</head>
<body>
<!-- 게시판 답변 -->
<form action="./BoardReplyView.bo" method="post" name="boardform">
<input type="hidden" name="BOARD_NUM" value="<%=board.getBOARD_NUM() %>">
<input type="hidden" name="BOARD_RE_REF" value="<%=board.getBOARD_RE_REF() %>">
<input type="hidden" name="BOARD_RE_LEV" value="<%=board.getBOARD_RE_LEV() %>">
<input type="hidden" name="BOARD_RE_SEQ" value="<%=board.getBOARD_RE_SEQ() %>">
<input type="hidden" name="BOARD_ID" value="<%=id %>">

<table>
	<tr align="center" valign="middle">
		<td colspan="5">MVC 게시판 reply</td>
	</tr>
	<tr>
		<td style="font-family:돋움; font-size:12" height="16">
			<div align="center">글쓴이</div>
		</td>
		<td>
			<%=id %>
		</td>
	</tr>
	<tr>
		<td style="font-family:돋움; font-size:12" height="16">
			<div align="center">제목</div>
		</td>
		<td>
			<input name="BOARD_SUBJECT" type="text" size="50"
			maxlength="100" value="Re: <%=board.getBOARD_SUBJECT() %>"/>
		</td>
	</tr>
	<tr>
		<td style="font-family:돋움; font-size:12">
			<div align="center">내 용</div>
		</td>
		<td>
			<textarea name="BOARD_CONTENT" cols="67" rows="15"></textarea>
		</td>
	</tr>
	<tr>
		<td style="font-family:돋움; font-size:12">
			<div align="center">비밀번호</div>
		</td>
		<td>
			<input name="BOARD_PASS" type="password">
		</td>
	</tr>
	
	<tr bgcolor="#cccccc">
		<td colspan="2" style="height:1px;">
		</td>
	</tr>
	<tr><td colspan="2">&nbsp;</td></tr>
	
	<tr align="center" vailgn="middle">
		<td colspan="5">
		<a href="javascript:replyboard()">[등록]</a>&nbsp;&nbsp;
		<a href="javascript:history.go(-1)">[뒤로]</a>
		</td>
	</tr>
</table>
</form>
</body>
</html>
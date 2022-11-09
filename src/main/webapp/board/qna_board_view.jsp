<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="net.board.db.*" %>
<%
	BoardBean board = (BoardBean)request.getAttribute("boarddata");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC 게시판</title>
</head>

<body>
	<!-- 게시판 수정 -->
	<table>
		<tr align="center" valign="middle">
			<td colspan="5">MVCC 게시판 detail</td>
		</tr>
		
		<tr>
			<td style="font-family: 돋움; font-size: 12pt" height="16">
				<div align="center">제목&nbsp;&nbsp;</div>
			</td>
			<td style="font-family: 돋움; font-size: 12pt">
				<%= board.getBOARD_SUBJECT() %>
			</td>
		</tr>
		
		<tr bgcolor="#cccccc">
			<td colspan="2" style="height: 1px;"></td>
		</tr>
		
		<tr>
			<td style="font-family: 돋움; font-size: 12pt">
				<div align="center">내용</div>
			</td>
			<td style="font-family: 돋움; font-size: 12pt">
				<table style="table-layout: fixed">
					<tr>
						<td valign="top" style="font-family: 돋움; font-size: 12pt">
							<%= board.getBOARD_CONTENT() %>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td style="font-family: 돋움; font-size: 12pt">
				<div align="center">파일 첨부</div>
			</td>
			<td style="font-family: 돋움; font-size: 12pt">
				<% if (!(board.getBOARD_FILE() == null)) { %>
				<a href="./boardupload/<%= board.getBOARD_FILE() %>">
					<%= board.getBOARD_FILE() %>
				</a>
				<% } %>
			</td>
		</tr>
		
		<tr bgcolor="#cccccc">
			<td colspan="2" style="height: 1px;"></td>
		</tr>
		
		<tr>
			<td colspan="2">&nbsp;</td>
		</tr>
		
		<tr align="center" valign="middle">
			<td colspan="5">
				<font size=2>
					<a href="./BoardReplyAction.bo?num=<%= board.getBOARD_NUM() %>">[답변]</a>&nbsp;&nbsp;
					<a href="./BoardModify.bo?num=<%= board.getBOARD_NUM() %>">[수정]</a>&nbsp;&nbsp;
					<a href="./BoardDeleteAction.bo?num=<%= board.getBOARD_NUM() %>">[삭제]</a>&nbsp;&nbsp;
					<a href="./BoardList.bo">[목록]</a>&nbsp;&nbsp;
				</font>
			</td>
		</tr>
	</table>
</body>
</html>
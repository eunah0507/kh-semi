package net.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("member ~~~ ");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		
		ActionForward forward = null;
		Action action = null;
		
			if (command.equals("/MemberLogin.me")) {
				// Login.me가 호출되면 loginForm을 띄움
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("./member/loginForm.jsp");
				// 이 if문이 처리돼서 else if로 가지 않고 맨 밑으로 감
				
			} else if (command.equals("/MemberJoin.me")) {
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setPath("./member/joinForm.jsp");
				
			} else if (command.equals("/MemberLoginAction.me")) {
				action = new MemberLoginAction();
				
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			// 회원가입
			} else if (command.equals("/MemberJoinAction.me")) {
				action = new MemberJoinAction();
				
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} /* else if (command.equals("/MemberListAction.me")) {
				action = new MemberListAction();
			
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (command.equals("/MemberViewAction.me")) {
				action = new MemberViewAction();
			
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} else if (command.equals("/MemberDeleteAction.me")) {
				action = new MemberDeleteAction();
			
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}*/
			
			if (forward != null) {
				if (forward.isRedirect()) {
					response.sendRedirect(forward.getPath());
					
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("get ~~ ");
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post ^^ ");
		doGet(request, response);
	}

}
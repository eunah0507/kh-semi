package net.boardq.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.boardq.db.BoardqBean;
import net.boardq.db.BoardqDAO;

public class BoardqAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BoardqDAO boardqdao = new BoardqDAO();
		BoardqBean boardqbean = new BoardqBean();
		ActionForward forward = new ActionForward();
		
		boardqbean.setQ_id(request.getParameter("q_id"));
		boardqbean.setQ_subject(request.getParameter("q_subject"));
		boardqbean.setQ_content(request.getParameter("q_content"));
		
		boolean result = boardqdao.boardInsert(boardqbean);
		
		if(result == false) {
			System.out.println("게시판 등록 실패");
			return null;
		}
		System.out.println("게시판 등록 완료");
		
		forward.setRedirect(true);
		forward.setPath("./BoardList.bo");
		return forward;
	}

}

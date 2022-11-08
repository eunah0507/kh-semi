package net.board.db;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public BoardDAO() { // 생성자
		
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			
		} catch (Exception ex) {
			System.out.println("DB 연결에 실패하였습니다." + ex);
			
			return;
		}
	}
	
	
	// 1. 게시글 수
	public int getListCount() {
		
		int x = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select count(*) from memberboard");
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				x = rs.getInt(1);
			}
			
		} catch (Exception ex) {
			System.out.println("getListCount 실패: " + ex);
			
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) {}
			if (con != null) try { con.close(); } catch (SQLException ex) {}
		}
		return x;
		
	}
	
	
	// 2. 게시판 레코드를 읽어옴
	public List getBoardList (int page, int limit) {
		
		String board_list_sql = "select * from "
				+ "(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_SUBJECT, "
				+ "BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF, BOARD_RE_LEV, "
				+ "BOARD_RE_SEQ, BOARD_READCOUNT, BOARD_DATE from "
				+ "(select * from memberboard order by "
				+ "BOARD_RE_REF desc, BOARD_RE_SEQ asc)) "
				+ "where rnum >= ? and rnum <= ?";
		
		List list = new ArrayList();
		
		int startrow = (page - 1) * 10 + 1; //
		int endrow = startrow + limit - 1; //
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(board_list_sql);
			
			pstmt.setInt(1, startrow);
			pstmt.setInt(2, endrow);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardBean board = new BoardBean();
				
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_ID(rs.getString("BOARD_ID"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				
				list.add(board);
			}
			return list;
			
		} catch (Exception ex) {
			System.out.println("getBoardList 읽어오기 실패: " + ex);
			
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) {}
			if (con != null) try { con.close(); } catch (SQLException ex) {}
		}
		return null;
		
		}

	
	// 3. 게시판 글쓰기 
	public boolean boardInsert(BoardBean board) {

		int num = 0;
		String sql = "";
		
		int result = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select max(board_num) from memberboard");
			// 가장 큰 값을 가져와서 밑에 if로 +1 해라
			rs = pstmt.executeQuery();
			
			if (rs.next())
				num = rs.getInt(1) + 1;
			else
				num = 1;
			
			sql = "insert into memberboard (BOARD_NUM, BOARD_ID, BOARD_SUBJECT, ";
			sql += "BOARD_CONTENT, BOARD_FILE, BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ, "
					+ "BOARD_READCOUNT, BOARD_DATE) values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBOARD_ID());
			pstmt.setString(3, board.getBOARD_SUBJECT());
			pstmt.setString(4, board.getBOARD_CONTENT());
			pstmt.setString(5, board.getBOARD_FILE());
			pstmt.setInt(6, num);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, 0);
			
			result = pstmt.executeUpdate();
			
			if (result == 0) return false;
			
			return true;
			
		} catch (Exception ex) {
			System.out.println("boardInsert 등록 실패: 333" + ex);
			
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) {}
			if (con != null) try { con.close(); } catch (SQLException ex) {}
		}
		return false;
		
	}

	
	// 글을 읽은 개수, 조회수 update(글 내용 확인 순간 호출됨)
	public void setReadCountUpdate(int num) throws Exception {
		
		String sql = "UPDATE memberboard set BOARD_READCOUNT = "
				+ "BOARD_READCOUNT + 1 where BOARD_NUM = " + num;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			System.out.println("setReadCountUpdate 글 읽은 개수 수정 실패: " + ex);
			
		} finally {
			try {
				if (pstmt != null) pstmt.close();
				if (con != null) con.close();
				
			} catch (Exception ex) {}
		}
		
	}
	
	
	// 내용 보기
	public BoardBean getDetail(int num) throws Exception {
		
		BoardBean board = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement("select * from memberboard where BOARD_NUM = ?");
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				board = new BoardBean();
				
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_ID(rs.getString("BOARD_ID"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_FILE(rs.getString("BOARD_FILE"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			}
			return board;
			
		} catch (Exception ex) {
			System.out.println("getDetail 내용 보기 실패: " + ex);
			
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException ex) {}
			if (pstmt != null) try { pstmt.close(); } catch (SQLException ex) {}
			if (con != null) try { con.close(); } catch (SQLException ex) {}
		}
		return null;
	}

}

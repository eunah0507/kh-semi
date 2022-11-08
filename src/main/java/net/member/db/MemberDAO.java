package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DataSource ds;
	
	public MemberDAO() { // 생성자
		
		try {
			Context init = new InitialContext();
			ds = (DataSource)init.lookup("java:comp/env/jdbc/OracleDB");
			
		} catch (Exception ex) {
			System.out.println("DB 연결 실패: " + ex);
			return;
		}
	}
	
	// 1. 회원
	public int isMember(MemberBean member) {
		
		String sql = "SELECT MEMBER_PW FROM MEMBER2 WHERE MEMBER_ID = ?";
		int result = -1;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMEMBER_ID());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				if (rs.getString("MEMBER_PW").equals(member.getMEMBER_PW())) {
					// true, false가 아닌 숫자로도 판별할 수 있음
					// 숫자로 주면 3번째, 4번째 경우도 판별 가능함
					result = 1; // 일치
					
				} else {
					result = 0; // 불일치
				}
				
			} else {
				result = -1; // 아이디가 존재하지 않음
			}
			
		} catch (Exception ex) {
			System.out.println("isMember 에러: " + ex);
			
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null) try {pstmt.close();} catch (SQLException ex) {}
			if (con != null) try {pstmt.close();} catch (SQLException ex) {}
		}
		return result;
		
	}
	
	// 2. 회원가입
	public boolean joinMember(MemberBean member) {
		String sql = "INSERT INTO MEMBER2 VALUES (?, ?, ?, ?, ?, ?)";
		int result = 0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getMEMBER_ID());
			pstmt.setString(2, member.getMEMBER_PW());
			pstmt.setString(3, member.getMEMBER_NAME());
			pstmt.setInt(4, member.getMEMBER_AGE());
			pstmt.setString(5, member.getMEMBER_GENDER());
			pstmt.setString(6, member.getMEMBER_EMAIL());
			
			result = pstmt.executeUpdate();
			// 여기에서 업데이트시킴
			
			if (result != 0) {
				return true;
				// 0이 아니라면 => 정상적으로 입력이 됐다면 true를 리턴해라
			}
			
		} catch (Exception ex) {
			System.out.println("joinMember 에러: 44" + ex);
		
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ex) {}
			if (pstmt != null) try {pstmt.close();} catch (SQLException ex) {}
			if (con != null) try {con.close();} catch (SQLException ex) {}
		}
		return false;
		// result가 false면 회원가입 실패
		
	}
	
}

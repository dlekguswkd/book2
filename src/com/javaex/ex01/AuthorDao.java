package com.javaex.ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	//필드
	
	//생성자
	/* 자동생성
	public AuthorDao () {
		
	} */
	
	//메소드 gs
	
	//메소드 일반
	
	//작가 등록
	public int insertAuthor(String name, String desc) {
		
		System.out.println(name);
		System.out.println(desc);
		System.out.println("저장 로직");
		
		int count = -1; 	// -1이 왔다는건 뭔가 문제가 있어서 일을 하지않았다는 뜻
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");	

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";						
			query += " insert into author ";			
			query += " values (null, ?, ?) ";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);		
			pstmt.setString(2, desc);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");
		

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		return count;
	} // 작가등록 끝
	
	// =================================================================
	
	//작가 삭제
	public int deleteAuthor(int id) {
		
		System.out.println(id);
		System.out.println("삭제 로직");
		
		int count = -1; 
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문 준비
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";
					
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
						
			// 실행
			count = pstmt.executeUpdate();
					 
			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return count;
		
	} //작가 삭제 끝
	
	// =================================================================
	
	//작가 전체 리스트 
	public List<AuthorVo> selectAuthorAll() {
		System.out.println("작가 전체 리스트");
		
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문 준비
			String query = "";
			query += " select author_id ";
			query += "		 , author_name ";
			query += "       , author_desc ";
			query += " from author ";
			query += " order by author_id asc ";
			
			//바인딩
			pstmt=conn.prepareStatement(query);
			
			// 실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			//리스트로 만들기
			while (rs.next()) {
				int id = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				AuthorVo authorVo = new AuthorVo(id, name, desc);
				authorList.add(authorVo);
			}
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return authorList;  //리스트의 주소를 리턴해준다
		
	} // 작가 전체 리스트 끝
	
	// =================================================================
	
	// 작가 수정
	public int updateAuthor(int id, String name, String desc) {
		
		System.out.println(id);
		System.out.println(name);
		System.out.println(desc);
		System.out.println("수정 로직");
		
		AuthorVo authorVo =new AuthorVo(id, name, desc);
		
		int count = -1; 
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");	

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " update author ";
			query += " set author_name = ? , ";
			query += "	   author_desc = ? ";
			query += " where author_id = ? ";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, id);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");
		

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		return count;
		
	}	// 작가 수정 끝
	
	// =================================================================
	
	// 작가 조회1개 (리스트로 담기)
	public List<AuthorVo> selectAuthorList(int id) {
		
		System.out.println(id);
		System.out.println("작가 조회");
		
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();
		
		int count = -1;
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");	

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " select author_id ";
			query += "		, author_name ";
			query += "		, author_desc ";
			query += " from author ";
			query += " where author_id = ? ";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				
				id = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				AuthorVo authorVo = new AuthorVo(id, name, desc);
				authorList.add(authorVo);
				
				System.out.println("author_id : " + id);
				System.out.println("author_name : " + name);
				System.out.println("author_desc : " + desc);
					
			}
		
			System.out.println( (count+2) + "건 조회 되었습니다.");


		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		return authorList;
		
	}	// 작가 조회1개(리스트로 담기) 끝
	
	// =================================================================
	
	// 작가 조회1개 (리스트 x)
	public AuthorVo selectAuthor(int id) {
		
		System.out.println(id);
		System.out.println("작가 조회");
		
		AuthorVo authorVo = new AuthorVo();
		
		int count = -1;
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");	

			// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " select author_id ";
			query += "		, author_name ";
			query += "		, author_desc ";
			query += " from author ";
			query += " where author_id = ? ";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				
				int aid = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				authorVo.setAuthorId(aid);
				authorVo.setAuthorName(name);
				authorVo.setAuthorDesc(desc);
					
			}
		
			System.out.println( (count+2) + "건 조회 되었습니다.");


		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리

			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		return authorVo;
		
	}	// 작가 조회1개(리스트 x) 끝
	
	
}

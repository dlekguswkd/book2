package com.javaex.ex03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	//필드
	// 똑같은 값을 다루는(1,2번에서)
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/book_db";
	private String id = "book";
	private String pw = "book";
			
	//생성자

	//메소드 gs
	
	//메소드 일반
	
	// 똑같은 행동을 하는 (1,2번에서)
	private void getConnection() {	// private으로 해서 딴데서 안쓰게 막아둠
		// DB연결메소드
		try {
			
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);	

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	// 똑같은 행동을 하는 (5번에서)
	private void close() {
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
	
	//작가 등록
	public int insertAuthor(String name, String desc) {
		
		System.out.println(name);
		System.out.println(desc);
		System.out.println("저장 로직");
		
		int count = -1; 
		
		//(1)(2)번 getconnection 메소드로 따로 정의하고 불러 온다
		// DB연결 메소드호출
		this.getConnection();
		

		try {

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
		

		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		//자원정리 메소드 호출
		this.close();

		
		
		return count;
	} // 작가등록 끝
	
	// =================================================================
	
	//작가 삭제
	public int deleteAuthor(int authorId) {
		
		System.out.println(authorId);
		System.out.println("삭제 로직");
		
		int count = -1; 
		
		getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문 준비
			String query = "";
			query += " delete from author ";
			query += " where author_id = ? ";
					
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, authorId);
						
			// 실행
			count = pstmt.executeUpdate();
					 
			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
		
	} //작가 삭제 끝
	
	// =================================================================
	
	//작가 전체 리스트 
	public List<AuthorVo> selectAuthorAll() {
		System.out.println("작가 전체 리스트");
		
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();
		
		this.getConnection();
		
		try {
			
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
				int authorId = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				AuthorVo authorVo = new AuthorVo(authorId, name, desc);
				authorList.add(authorVo);
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		return authorList;  //리스트의 주소를 리턴해준다
		
	} // 작가 전체 리스트 끝
	
	// =================================================================
	
	// 작가 수정
	public int updateAuthor(int authorId, String name, String desc) {
		
		System.out.println(authorId);
		System.out.println(name);
		System.out.println(desc);
		System.out.println("수정 로직");
		
		AuthorVo authorVo =new AuthorVo(authorId, name, desc);
		
		int count = -1; 

		this.getConnection();
		
		try {

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
			pstmt.setInt(3, authorId);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");
		

		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return count;
		
	}	// 작가 수정 끝
	
	// =================================================================
	
	// 작가 조회1개
	public List<AuthorVo> selectAuthorList(int authorId) {
		
		System.out.println(authorId);
		System.out.println("작가 조회");
		
		List<AuthorVo> authorList = new ArrayList<AuthorVo>();
		
		int count = -1;
		this.getConnection();
	
		try {

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
			pstmt.setInt(1, authorId);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				
				authorId = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				AuthorVo authorVo = new AuthorVo(authorId, name, desc);
				authorList.add(authorVo);
				
				System.out.println("author_id : " + authorId);
				System.out.println("author_name : " + name);
				System.out.println("author_desc : " + desc);
					
			}
		
			System.out.println( (count+2) + "건 조회 되었습니다.");


		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		
		return authorList;
		
	}	// 작가 조회1개 끝
	
	
}

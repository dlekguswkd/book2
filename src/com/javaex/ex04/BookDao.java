package com.javaex.ex04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

	//필드
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
	
	//책 등록
	public int insertBook(String title, String pubs, String pubDate, int authorId) {
		
		System.out.println(title);
		System.out.println(pubs);
		System.out.println(pubDate);
		System.out.println(authorId);
		System.out.println("저장 로직");
		
		int count = -1; 	
		
		this.getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비				
			String query = "insert into book values (null, ?, ?, ?, ?)";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);	
			pstmt.setString(2, pubs);	
			pstmt.setString(3, pubDate);	
			pstmt.setInt(4, authorId);

			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 등록되었습니다.");
		

		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		
		return count;
	} // 책 등록 끝
	
	// =================================================================
	
	// 책 수정
	public int updateBook(int bookId,String title, String pubs, String pubDate, int authorId) {
		
		System.out.println(bookId);
		System.out.println(title);
		System.out.println(pubs);
		System.out.println(pubDate);
		System.out.println(authorId);
		System.out.println("수정 로직");
		
		int count = -1; 
		
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = ""; 
			query += " update book ";
			query += " set title = ? ";
			query += "	 , pubs = ? ";
			query += "	 , pub_date = ? ";
			query += " 	 , author_id = ? ";
			query += " where book_id = ? ";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, pubs);
			pstmt.setString(3, pubDate);
			pstmt.setInt(4, authorId);
			pstmt.setInt(5, bookId);
			
			// 실행
			count = pstmt.executeUpdate();

			// 4.결과처리
			System.out.println(count + "건 수정되었습니다.");
		

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		
		return count;
		
	}	// 책 수정 끝
	
	// =================================================================
		
	
	//책 삭제
	public int deleteBook(int id) {
		
		System.out.println(id);
		System.out.println("삭제 로직");
		
		int count = -1; 
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문 준비
			String query = "";
			query += " delete from book ";
			query += " where book_id = ? ";
					
			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
						
			// 실행
			count = pstmt.executeUpdate();
					 
			// 4.결과처리
			System.out.println(count + "건 삭제되었습니다.");
			
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		return count;
		
	} //책 삭제 끝
	
	// =================================================================
	// 책 조회1개
	public BookVo selectBook(int id) {
		
		System.out.println(id);
		System.out.println("책 조회");
		
		BookVo bookVo = new BookVo();
		
		int count = -1;
		
		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			//sql문 준비
			String query = "";
			query += " select book_id ";
			query += "		, title ";
			query += "		, pubs ";
			query += "		, pub_date ";
			query += "		, author_id ";
			query += " from book ";
			query += " where book_id = ? ";
		
			// 바인딩 (말랑말랑하게 해주기)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				
				int bid = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int aid = rs.getInt("author_id");
				
				bookVo.setBookId(bid);
				bookVo.setTitle(title);
				bookVo.setPubs(pubs);
				bookVo.setPubDate(pubDate);
				bookVo.setAuthorId(aid);
				
					
			}
		
			System.out.println( (count+2) + "건 조회 되었습니다.");


		}  catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		
		return bookVo;
		
	}	// 작가 조회1개 끝
	
	
	// =================================================================
		
	//책 전체 리스트 
	public List<BookVo> selectBookAll() {
		System.out.println("책 전체 리스트");
		
		List<BookVo> bookList = new ArrayList<BookVo>();
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			// sql문 준비
			String query = "";
			query += " select b.book_id ";
			query += "		, b.title ";
			query += "		, b.pubs ";
			query += "		, b.pub_date ";
			query += "		, b.author_id ";
			query += "        , a.author_name ";
			query += "        , a.author_desc ";
			query += " from book b ";
			query += " left join author a ";
			query += " on b.author_id = a.author_id ";
			
			//바인딩
			pstmt=conn.prepareStatement(query);
			
			// 실행
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			//리스트로 만들기
			while (rs.next()) {
				int bid = rs.getInt("book_id");
				String title = rs.getString("title");
				String pubs = rs.getString("pubs");
				String pubDate = rs.getString("pub_date");
				int aid = rs.getInt("author_id");
				String name = rs.getString("author_name");
				String desc = rs.getString("author_desc");
				
				
				BookVo bookVo = new BookVo(bid, title, pubs, pubDate, aid, name, desc);
				bookList.add(bookVo);
			}
			
			
		}  catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		this.close();
		return bookList;  
		
	} // 책 전체 리스트 끝
	


}

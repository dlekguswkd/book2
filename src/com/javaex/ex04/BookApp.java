package com.javaex.ex04;

import java.util.List;

public class BookApp {

	public static void main(String[] args) {
		
		// 등록 수정 삭제 조회1 조회전체
		
		BookDao bookDao = new BookDao();
		
		//등록
		//int count = bookDao.insertBook("황일영", "학원강사");
		
		//수정
		//int count = bookDao.updateBook(9, "황일영", "강사"); 
		
		//삭제
		//bookDao.deleteBook(8);
		
		//조회1
		//BookVo bookVo = bookDao.selectBook(7); 
		//System.out.println(bookVo); 

		//조회전체
		//List<BookVo> bookList = bookDao.selectBookAll(); // new 리스트 만들어서 주소만 전달한다
		//System.out.println(bookList);
		//제목만 출력
		//for (int i=0; i<bookList.size(); i++) {
		//	System.out.println(bookList.get(i).gettitle());
		//}
				

	}

}

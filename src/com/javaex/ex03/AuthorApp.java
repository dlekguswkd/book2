package com.javaex.ex03;

import java.util.List;

public class AuthorApp {

	public static void main(String[] args) {
		
		// 등록 수정 삭제 조회1 조회전체
		
		AuthorDao authorDao = new AuthorDao();
		
		//등록
		//int count = authorDao.insertAuthor("다현", "수강생");
		
		//삭제
		//authorDao.deleteAuthor(10);
		
		//조회전체
		//List<AuthorVo> authorList = authorDao.selectAuthorAll(); // new 리스트 만들어서 주소만 전달한다
		//System.out.println(authorList);
		//이름만 출력
		//for (int i=0; i<authorList.size(); i++) {
		//	System.out.println(authorList.get(i).getAuthorName());
		//}
		
		//수정
		//int count = authorDao.updateAuthor(9, "황일영", "학원강사"); 
	
		
		//조회1 (리스트로 담아서)
		//List<AuthorVo> authorList2 = authorDao.selectAuthorList(7); 
		
		//조회1 리스트 x
	    //AuthorVo authorvo = authorDao.selectAuthor(7); 
		
		

	}

}

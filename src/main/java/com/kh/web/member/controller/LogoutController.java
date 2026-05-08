package com.kh.web.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout.do")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LogoutController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 정의 => 인증에 성공한 사용자의 정보를 서버측 저장소인 세션에 저장한것이다 
		// 로그아웃 정의 => 세션에 저장되어 있는 인증된 사용자의 정보를 제거하는것 
		
		HttpSession session = request.getSession();
		//session.removeAttribute("userInfo");
		session.invalidate(); //만료시킨다, 무효화한다라는 의미가 있다
		
		// 응답할때 값이 없기 때문에 sendRedirect사용한다
		response.sendRedirect("/kh");
	
	
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

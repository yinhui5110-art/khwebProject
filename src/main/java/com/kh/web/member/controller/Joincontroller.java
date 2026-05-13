package com.kh.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.web.member.model.dto.MemberDto;
import com.kh.web.member.model.service.MemberService;

@WebServlet("/join.do")
public class JoinController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JoinController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// post방식으로 요청
		// 1) 인코딩 설정을 먼저 해줘야한다. 인코딩형식을 utf8로
		request.setCharacterEncoding("UTF-8");
		
		// 2) request 객체로부터 요청 시 전달 값 Get
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String userName = request.getParameter("userName");	
		String email = request.getParameter("email");	
		
		// 3) 가공
		MemberDto member = new MemberDto(userId,userPwd,userName,email);
		
		// 4) 요청처리 -> Service단으로 전달
		
		int result = new MemberService().insertMember(member);
		
		// 5) 회원거입 요청이 성공했는가 / 실패했는가에 따라서
		// 	  응답화면을 다르게 지정한다
		if(result > 0) { //이것은 성공시
			/*
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			*/
			// sendRedirect방식
			// localhost:8089/kh -> 
			
			// /kh
			response.sendRedirect("/kh");
		}else {// 실패했을때
			request.setAttribute("massage", "회원가입 실패~");
			request.getRequestDispatcher("/WEB_INF/views/common/fail_page.jsp")
							.forward(request, response);
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.kh.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.web.member.model.dto.MemberDto;
import com.kh.web.member.model.service.MemberService;
@WebServlet("/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 절차
		// 1) GET? POST ? => post로 왔기때문에 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값이 있는가? => post는 무조건 있다
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
	
		// 3) 가공
		MemberDto member = new MemberDto();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		// 4) 요청처리 하기~ => Service단으로 전달 => 전통적인 session방식 로그인 => 규모가 작은 프로젝트에서 구현된다
		MemberDto loginMember = new MemberService().login(member);
		// 성공했을 경우 : 조회 성공한 컬럼값을 필드에 담은 멤버 객체의 주소 값
		// 실패했을 경우 : null값
		
		// 5) 결과값 반환 /  응답화면 지정하기
		/*
		 * session : 모든 JSP와 Servlet에서 값을 꺼내서 쓸 수 있는 저장소
		 * 			단, session에 값이 지워지기 전까지
		 * 			세션종료시점 : 브라우저 종료, 서버 종료 코드로 지운다
		 * 
		 * request : 해당 request를 포워딩한 응답 JSP에서까지만 쓸 수 있다	
		 * 			 요청부터 응답 까지만 사용이 가능하다
		 * 
		 * 
		 */
		
		
		
		if(loginMember != null){
			request.setAttribute("userInfo", loginMember);
			//response.sendRedirect("/kh");
			//request.getRequestDispatcher("/index.jsp").forward(request, response);
			
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", loginMember);
			session.setAttribute("alertMsg", "로그인 성공 추카포카~") ;
			response.sendRedirect("/kh");
		
		
		}else {
			request.setAttribute("message", "로그인에 실패했습니다.");
			request.getRequestDispatcher("/WEB-INF/views/common/fail_page.jsp")
						.forward(request, response);
		}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

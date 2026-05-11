package com.kh.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.web.member.model.dto.MemberDto;

@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 값 뽑기 => 제목, 내용 => BoardDto로 가공
		// 		  => 파일     =>  이거저거          =>DTO로 가공
		//
	String boardTitle = request.getParameter("baordTitle");
	System.out.println(boardTitle);
	
	// form태그로 요청 했을 때 multipart/form-data형식으로 요청한다면
	//request.getParatemeter로는 요청 시 전달값을 뽑아낼 수 없다
	
	HttpSession session = request.getSession();
	MemberDto member = (MemberDto)session.getAttribute("userInfo");
	if(member == null) {
		session.setAttribute("message", "글쓰기는 로그인 이후 가능합니다!");
		response.sendRedirect("/kh/fail.do");
		return;
	  }
	
	// 요즘 가장 일반적인 방법은 => 테이블에 컬럼을 하나 만들기 => ADMIN / USER
	
	// 1) 요청이 multipart방식으로 잘 왔는가를 확인
	if(ServletFileUpload.isMultipartContent(request)) {
		//System.out.println("요청입니다~");
		
		// 2) 파일 전송 시 필요한 세팅
		// 2_1) 파일 용량 제한
		/*
		 * 1bit = > 한 칸
		 * 
		 * bit X 8 => 1Byte X 1024하면 =>KByte => MByte => GByte => TByte => PByte =>...
		 * 
		 * 10MegaByte
		 * 
		 */
		int maxSize = 10 * 1024 * 1024;
		
		// 2_2. 서버의 파일 저장할 경로를 얻어내야 한다
		// PageContext
		// HttpServletRequest
		// HttpSession 모든 서블릿
		// ServletContext => getRealPath()
		// request.getServletContext()
		ServletContext application = session.getServletContext();
		String savePath = application.getRealPath("/resources/board_upfiles");
		System.out.println(savePath);
		// 장점
		// 동적으로 실제 결오 확인 | 서버환경에 관계없이 동작한다
		// 단점
		// WAR파일 베포 시 파일이 사라진다
		
		// 3. 파일 업로드
		
		// a.jpg  a2.jpg a3.jpg
		// kakaoTalk_20260511_151131 123 .jsp
		
	}
	
	
	
	
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.kh.web.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.web.board.model.dto.AttachmentDto;
import com.kh.web.board.model.dto.BoardDto;
import com.kh.web.board.model.service.BoardService;
import com.kh.web.common.MyRenamePolicy;
import com.kh.web.member.model.dto.MemberDto;
import com.oreilly.servlet.MultipartRequest;

@WebServlet("/update.bo")
public class BoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BoardUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 값뽑기가 안된다 String title = request.getParameter("boardTitle");
		if(ServletFileUpload.isMultipartContent(request)) {
			
			int maxSize = 1024 * 1024 *10;
			String savePath = request.getServletContext().getRealPath("/resources/board_upfiles");
			
			// Mutipart객체를 생성 => 파일 업로드
			
			MultipartRequest multiRequest = 
					new MultipartRequest(request, savePath, maxSize,"UTF-8", new MyRenamePolicy());
			
			// case 1. 첨부파일이 없다              => BAORD UPDATE + AT X
			// case 2. 기존 첨부파일 O, 새 첨부파일 O => BAORD UPDATE + AT UPDATE
			// case 3. 기존 첨부파일 X, 새 첨부파일 O => BAORD UPDATE + AT INSERT
			
			String boardTitle = multiRequest.getParameter("boardTitle");
			String boardContent = multiRequest.getParameter("boardContent");
			Long boardNo = Long.parseLong(multiRequest.getParameter("boardNo"));
			
			Long uertNo = ((MemberDto)request.getSession().getAttribute("userInfo")).getUserNo();
			BoardDto board = new BoardDto();
			board.setBoardNo(boardNo);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			board.setUserNo(uertNo);
			
			AttachmentDto at = null;
			if(multiRequest.getOriginalFileName("reUpfile")!= null) {
				at = new AttachmentDto();
				at.setOriginName(multiRequest.getOriginalFileName("reUpfile"));
				at.setChangeName(multiRequest.getFilesystemName("reUpfile"));
				at.setFilePath("resources/board_upfiles");
				at.setFileLevel(2);
				at.setBoardType("C");
				// INSERT / UPDATE
				// INSERT => 어떤게시글에 달리는 첨부파일인가 => REF_BNO
				// UPDATE => 원래 파일리 몇번째 행인가?     => FILE_NO
				if(multiRequest.getParameter("fileNo")!= null) {
					at.setFileNo(Long.parseLong(multiRequest.getParameter("fileNo")));
					
				}else {
					at.setRefBno(boardNo);
				}
				
			}// 데이터 가공
			// 1. 기능 만들어보기 => 2. 요구사항분석 => 3. SQL문을 먼저 생각해보고 완성한 뒤에 => 코드 작성해야 한다
			int result = new BoardService().updateBoard(board, at);
			HttpSession session = request.getSession();
			String key ="";
			String value ="";
			String path ="";

			if(result > 0) {
				//session.setAttribute("alerMsg", "게시글 수정 성공~~");
				//response.sendRedirect(request.getContextPath()+ "/detail.bo?boardNo="+boardNo);
				key ="alertMsg";
				value ="게시글수정성공";
				path = request.getContextPath() + "/detail.bo?boardNo=" + boardNo;
			
			}else {
				//session.setAttribute("message", "게시글 수정 실패..");
				//response.sendRedirect(request.getContextPath()+"/fail.do");
				key ="message";
				value ="게시글 수정 실패..";
				path = request.getContextPath() + "/fail.do";
				
			}
			session.setAttribute(key, value);
			response.sendRedirect(path);
			
		}
		
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.kh.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.web.board.model.dto.BoardDto;
import com.kh.web.board.model.service.BoardService;

@WebServlet("/boards.im")
public class ImageBoardListController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public ImageBoardListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. 값을 뽑는다 X
		// 2. 가공한다 X
		// 3. 서비스호출한다
		// 4. 응답화면지정 한다
		
		List<BoardDto> list = new BoardService().selectImageList();
		System.out.println(list);
		request.setAttribute("boards",list);
		request.getRequestDispatcher("/WEB-INF/views/imge_board/list.jsp").forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

package com.kh.web.board.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.web.ajax.model.dto.ReplyDto;
import com.kh.web.board.model.dao.BoardDao;
import com.kh.web.board.model.dto.AttachmentDto;
import com.kh.web.board.model.dto.BoardDto;
import com.kh.web.board.model.dto.BoardResponse;
import com.kh.web.board.model.dto.ImageResponse;
import com.kh.web.common.Template;
import com.kh.web.common.model.dto.PageInfo;

public class BoardService {
	private BoardDao bd = new BoardDao();
	
	public int selectBoardCount() {
		SqlSession sqlSession = Template.getSqlSession();
		
		int listCount = bd.selectBoardCount(sqlSession);
		
		sqlSession.close();
		
		return listCount;
	}

	public List<BoardDto> selectBoardList(PageInfo pi){
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<BoardDto> boards = bd.selectBoardList(sqlSession, pi);
		
		sqlSession.close();
		
		return boards;
		
	}

	
	public BoardResponse selectBoard(Long boardNo){
		SqlSession sqlSession = Template.getSqlSession();
		
		int result = bd.increaseCount(sqlSession, boardNo);
		BoardResponse br = null;
		
		if(result > 0) {
			sqlSession.commit();
			BoardDto board = bd.selectBoard(sqlSession, boardNo);
			AttachmentDto attachment = bd.selectAttachment(sqlSession, boardNo);
			br = new BoardResponse();
			br.setBoard(board);
			br.setAttachment(attachment);
			return br;
			
		}
		  sqlSession.close();

		return null;

	}
	public int insertBoard(BoardDto board, AttachmentDto at) {
		
		SqlSession session = Template.getSqlSession();
		
		String newTitle = board.getBoardTitle().replaceAll("<", "&lt;");
		board.setBoardTitle(newTitle);

		int result = bd.insertBoard(session, board);
		int atResult = 1;
		if(at != null) {
			at.setRefBno(board.getBoardNo());
			atResult = bd.insertAttachment(session, at);
		}
		if(result * atResult > 0) {
			session.commit();
		} else {
			session.rollback();
		
		}
		session.close();
		
		return (result * atResult);
		
	}
	
	public int updateBoard(BoardDto board, AttachmentDto at) {
		SqlSession sqlSession = Template.getSqlSession();
		
		// 1. WEB_BOARD => UPDATE
		// --------------------------
		// 2. WEB_ATTACHMENT => UPDATE
		// 3. WEB_ATTACHMENT => INSERT
		
		int result = bd.updateBoard(sqlSession, board);
		
		if(at != null) {
			if(at.getFileNo() != null) {
				result *= bd.updateAttachment(sqlSession, at);
				
			}else {
				result *= bd.insertAttachment(sqlSession, at);
			}
			
		}
		if(result > 0) {
			sqlSession.commit();
		}else {
			sqlSession.rollback();
		}
		sqlSession.close();
		return result;
	}
	public int insertImage(BoardDto board, List<AttachmentDto> files) {
		SqlSession sqlSession = Template.getSqlSession();
		int result = 0;
		try {
		// 1번 board를 전달 
		result = bd.insertImage(sqlSession, board);
		
		// 2번 Attachment
		// 게시글 인서트가 성공!
		if(result > 0) {
			
			for(AttachmentDto file : files) {
				file.setRefBno(board.getBoardNo());
				
				result *= bd.insertAttachment(sqlSession, file);
				
				if(result == 0) {
					new RuntimeException();
				}
			}
		}
		
		}catch(Exception e) {
			sqlSession.rollback();
			result = 0;
			e.printStackTrace();
		}finally {
		// 3번 전부 다 성공했으면 커밋
		if(result > 0 ) {
			sqlSession.commit();
			
		}else {
			sqlSession.rollback();
		}
		sqlSession.close();
		
	}
		return result;
	}
	
	public List<BoardDto> selectImageList(){
		
		SqlSession sqlSession = Template.getSqlSession();
		
		List<BoardDto> list = bd.selectImageList(sqlSession);
		
		sqlSession.close();
		
		return list;
	}
	
	
	public ImageResponse selectImageDetail(Long boardNo) {
		
		SqlSession sqlSession = Template.getSqlSession();
		
		ImageResponse ir = bd.selectImageDetail(sqlSession, boardNo);
		
		sqlSession.close();
		return ir;
		
	}
	public int insertReply(ReplyDto reply) {
		
		SqlSession sqlSession = Template.getSqlSession();
		int result = bd.insertReply(sqlSession, reply);
		
		if(result > 0) {
			sqlSession.commit();
			
		}
		sqlSession.close();
		
		return result;
		
	}
	
	public List<ReplyDto> selectReply(Long boardNo){
		SqlSession sqlSession = Template.getSqlSession();
		
		List<ReplyDto> reply = bd.selectReply(sqlSession, boardNo);
		
		sqlSession.close();
		
		return reply;
	
	
	
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

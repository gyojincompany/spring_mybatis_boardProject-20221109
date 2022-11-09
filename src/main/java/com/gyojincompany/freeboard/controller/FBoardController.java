package com.gyojincompany.freeboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gyojincompany.freeboard.dao.mapper.IDao;

@Controller
public class FBoardController {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@RequestMapping(value = "joinMember")
	public String joinMember() {
		
		return "joinMember";
	}
	
	@RequestMapping(value = "joinOk", method = RequestMethod.POST)
	public String joinOk(HttpServletRequest request) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.joinMemberDao(request.getParameter("mid"), request.getParameter("mpw"), request.getParameter("mname"), request.getParameter("memail"));
		
		return "joinOk";
	}
}

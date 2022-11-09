package com.gyojincompany.freeboard.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String joinOk(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(request.getParameter("mid"));
		//checkIdFlag 값이 1이면 이미 가입하려는 아이디가 db에 존재, 0이면 가입 가능
		model.addAttribute("checkIdFlag", checkIdFlag);
		
		if (checkIdFlag == 0) {
			dao.joinMemberDao(request.getParameter("mid"), request.getParameter("mpw"), request.getParameter("mname"), request.getParameter("memail"));
			model.addAttribute("mname", request.getParameter("mname"));
		}
		return "joinOk";
	}
	
	@RequestMapping(value = "checkId")
	public String checkId(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(request.getParameter("checkId"));
		//checkIdFlag 값이 1이면 이미 가입하려는 아이디가 db에 존재, 0이면 가입 가능
		model.addAttribute("checkIdFlag", checkIdFlag);
		
		return "checkId";
	}
	
	
	
}

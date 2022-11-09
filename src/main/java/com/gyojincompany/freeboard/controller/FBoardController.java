package com.gyojincompany.freeboard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gyojincompany.freeboard.dao.mapper.IDao;
import com.gyojincompany.freeboard.dto.MemberDto;

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
	
	@RequestMapping(value = "login")
	public String login() {
		
		return "login";
	}
	
	@RequestMapping(value = "loginOk", method = RequestMethod.POST)
	public String loginOk(HttpServletRequest request, Model model) {
		
		String mid = request.getParameter("mid");
		String mpw = request.getParameter("mpw");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		int checkIdFlag = dao.checkIdDao(mid);//1이면 아이디 존재 0이면 기존 가입한 아이디 없음
		int checkPwFlag = dao.checkPwDao(mid, mpw);//1이면 아이디 비번 모두 일치 0이면 비밀번호 틀림
		
		model.addAttribute("checkIdFlag", checkIdFlag);
		model.addAttribute("checkPwFlag", checkPwFlag);
		model.addAttribute("mid", mid);
		
		if(checkPwFlag == 1) {//로그인 성공시 세션에 아이디와 로그인유효값을 설정
			
			HttpSession session = request.getSession();
			
			session.setAttribute("sessionId", mid);
			session.setAttribute("ValidMem", "yes");
			
			MemberDto dto = dao.memberInfoDao(mid);
			String mname= dto.getMname();
			model.addAttribute("mname", mname);
			
		} else {
			
			model.addAttribute("mname", "손님");
		}
		
		return "loginOk";
	}
	
	@RequestMapping(value = "writeForm")
	public String writeForm(HttpServletRequest request, Model model) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		HttpSession session = request.getSession();
		String sid = (String) session.getAttribute("sessionId");
		
		System.out.println(sid);
		
		if(sid == null) {
			return "redirect:login";
		} else {		
			MemberDto dto = dao.memberInfoDao(sid);
			String mname= dto.getMname();
			String mid = dto.getMid();
			model.addAttribute("mname", mname);
			model.addAttribute("mid", mid);
			
			return "writeForm";
		}
	}
	
	@RequestMapping(value = "write")
	public String write(HttpServletRequest request) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		HttpSession session = request.getSession();
		String sid = (String) session.getAttribute("sessionId");
		
//		String mname="";
//		String mid="";
//		
//		if(sid.equals(null)) {
//			mname="손님";
//			mid="guest";
//		} else {
		
			MemberDto dto = dao.memberInfoDao(sid);
			String mname= dto.getMname();
			String mid = dto.getMid();		
//		}
		String ftitle = request.getParameter("ftitle");
		String fcontent = request.getParameter("fcontent");
		
		dao.writeDao(mid, mname, ftitle, fcontent);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "logout")
	public String logout(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();//로그아웃(세션삭제)
		
		return "logout";
	}
}

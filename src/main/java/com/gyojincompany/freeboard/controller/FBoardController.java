package com.gyojincompany.freeboard.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gyojincompany.freeboard.dao.mapper.IDao;
import com.gyojincompany.freeboard.dto.FreeBoardDto;
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
	
	@RequestMapping(value = "list")
	public String list(Model model, HttpServletRequest request) {
		
		IDao dao = sqlSession.getMapper(IDao.class);
		ArrayList<FreeBoardDto> boardDtos =  dao.listDao();
		
		HttpSession session = request.getSession();//현재 세션 가져오기
		  
		String sid = (String) session.getAttribute("sessionId");
		
		int idflag = 0;
		
		if(sid != null) {//로그인이 되어있는 경우
			idflag = 1;
			model.addAttribute("sid", sid);
			
		}
		model.addAttribute("idflag", idflag);//1이면 로그인중 0이면 비로그인중
		
		
		model.addAttribute("boardSum", boardDtos.size());		
		model.addAttribute("list", boardDtos);
		
		return "list";
	}
	
	@RequestMapping(value = "contentView")
	public String contentView(HttpServletRequest request, Model model) {
		String fnum = request.getParameter("fnum");
		
		IDao dao = sqlSession.getMapper(IDao.class);		
		
		FreeBoardDto fbdto = dao.contentView(fnum);
		
		HttpSession session = request.getSession();//현재 세션 가져오기
		
		String sid = (String) session.getAttribute("sessionId");//현재 세션에 로그인 되어 있는 아이디 가져오기
		
		String fid = fbdto.getFid();//현재 보고 있는 글을 쓴 아이디
		
		int idflag = 0;
		
		if((sid != null) && (sid.equals(fid))) {
			idflag = 1;
		}
		model.addAttribute("idflag", idflag);//idflag==1이면 수정,삭제 권한 설정
		
//		int fhit = fbdto.getFhit();
//		fhit = fhit+1;		
		dao.upHit(fnum);//조회수 증가 함수
		
		model.addAttribute("fbdto", fbdto);
		
		return "contentView";
	}
	
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request) {
		
		String fnum = request.getParameter("fnum");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.deleteDao(fnum);
		
		return "redirect:list";
	}
	
	@RequestMapping(value = "modifyView")
	public String modifyView(HttpServletRequest request, Model model) {
		
		String fnum = request.getParameter("fnum");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		FreeBoardDto fbdto = dao.contentView(fnum);
		
		model.addAttribute("fbdto", fbdto);
		
		return "modifyView";
	}
	
	@RequestMapping(value = "modify")
	public String modify(HttpServletRequest request) {
		
		String fnum = request.getParameter("fnum");
		String fname = request.getParameter("fname");
		String ftitle = request.getParameter("ftitle");
		String fcontent = request.getParameter("fcontent");
		
		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.modifyDao(fnum, fname, ftitle, fcontent);
		
		return "redirect:list";
	}
	
}

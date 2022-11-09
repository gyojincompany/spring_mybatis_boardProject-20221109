package com.gyojincompany.freeboard.dao.mapper;

public interface IDao {
	
	//member 관련 메서드
	public void joinMemberDao(String mid, String mpw, String mname, String memail);//회원 가입
	public int checkIdDao(String mid);//회원 가입 여부 확인(1반환이면 이미 존재, 0이면 가입 가능)
}

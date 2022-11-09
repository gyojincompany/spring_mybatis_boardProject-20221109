package com.gyojincompany.freeboard.dto;

import java.sql.Timestamp;

public class FreeBoardDto {
	
	private int fnum;//게시판 번호
	private String fid;//글쓴이 아이디
	private String fname;//글쓴이 이름
	private String ftitle;//글제목
	private String fcontent;//글내용
	private int fhit;//조회수
	private Timestamp fdate;//글쓴 일시
	
	public FreeBoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}	

	public FreeBoardDto(int fnum, String fid, String fname, String ftitle, String fcontent, int fhit, Timestamp fdate) {
		super();
		this.fnum = fnum;
		this.fid = fid;
		this.fname = fname;
		this.ftitle = ftitle;
		this.fcontent = fcontent;
		this.fhit = fhit;
		this.fdate = fdate;
	}	

	public int getFhit() {
		return fhit;
	}

	public void setFhit(int fhit) {
		this.fhit = fhit;
	}



	public int getFnum() {
		return fnum;
	}

	public void setFnum(int fnum) {
		this.fnum = fnum;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getFcontent() {
		return fcontent;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	public Timestamp getFdate() {
		return fdate;
	}

	public void setFdate(Timestamp fdate) {
		this.fdate = fdate;
	}
	
	
	
	
}

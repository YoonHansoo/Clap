package com.clap.main;

import com.clap.vo.member.MemberVO;
public class LoginSession {
	
	
	// 로그인 정보를 임시저장하는 MemberVO 객체
	// 로그아웃시 null로 만들어줘야 함.
	public static MemberVO session = null;
}

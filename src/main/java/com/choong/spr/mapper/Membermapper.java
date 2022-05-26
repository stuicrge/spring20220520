package com.choong.spr.mapper;

import java.util.List;

import com.choong.spr.domain.MemberDto;

public interface Membermapper {

//	int addMember(MemberDto member);

	int insertMember(MemberDto member);

	int countMemberId(String id);

	int countMemberEmail(String email);

	int countMembernickName(String nickName);

	List<MemberDto> listMember();

	MemberDto selectMemberById(String id);

	int deleteMemberById(String id);

}

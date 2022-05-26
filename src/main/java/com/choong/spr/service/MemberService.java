package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.Membermapper;

@Service
public class MemberService {

	@Autowired
	private Membermapper mapper;
	
	public boolean addMember(MemberDto member) {
		
		
		return mapper.insertMember(member)==1;
	}

	public boolean hasMemberId(String id) {
		// TODO Auto-generated method stub
		return mapper.countMemberId(id)>0;
	}

	public boolean hasMemberEmail(String email) {
		// TODO Auto-generated method stub
		return mapper.countMemberEmail(email) > 0;
	}

	public boolean hasMembernickName(String nickName) {
		// TODO Auto-generated method stub
		return mapper.countMembernickName(nickName) > 0;
	}

	public List<MemberDto> listMember() {
		// TODO Auto-generated method stub
		return mapper.listMember();
	}

	public MemberDto getMemberById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectMemberById(id);
	}

	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		if (member.getPassword().equals(dto.getPassword())) {
			return mapper.deleteMemberById(dto.getId()) == 1;
		}
		
		return false;
	}

}

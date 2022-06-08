package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.Membermapper;
import com.choong.spr.mapper.ReplyMapper;

@Service
public class MemberService {

	@Autowired
	private Membermapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	public boolean addMember(MemberDto member) {
		
		//평문암호(password)를 암호화
		String	encodedPassword = passwordEncoder.encode(member.getPassword());
		
		member.setPassword(encodedPassword);
		
		int cnt1=mapper.insertMember(member);
		int cnt2=mapper.insertAuth(member.getId(),"ROLE_USER");
		
		return cnt1 == 1 && cnt2==1;
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

	@Transactional
	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		
		String rawPW = dto.getPassword();
		String encodedPW = member.getPassword();
		
		if (passwordEncoder.matches(rawPW,encodedPW)) {
			
			replyMapper.deleteByMemberId(dto.getId());
			
			List<BoardDto> boardList = boardMapper.listByMemberId(dto.getId());
			for(BoardDto board : boardList) {
				
				replyMapper.deleteByBoardId(board.getId());
			}
			
			boardMapper.deleteByMemberId(dto.getId());
			
			
			 mapper.deleteAuthById(dto.getId());
			
			int cnt2 = mapper.deleteMemberById(dto.getId());
			
			return cnt2 == 1;
		}
		
		return false;
	}

	public boolean modifyMember(MemberDto dto, String oldPassword) {
		//db에서 member읽어서
		MemberDto oldMember = mapper.selectMemberById(dto.getId());
		
		String encodedPW = oldMember.getPassword();
		//기존  password가 일치할때까지만 계속 진행
		if(passwordEncoder.matches(oldPassword,encodedPW)) {
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			return mapper.updateMember(dto)==1;
		}
		return false;
	}

	public void initPassword(String id) {
		// TODO Auto-generated method stub
		
		String pw = passwordEncoder.encode(id);
		
		mapper.updatePasswordById(id,pw);
	}

}

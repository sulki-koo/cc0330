package cookcloud.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cookcloud.entity.Member;
import cookcloud.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public List<Member> getMemberList() {
		return memberRepository.findAllNotDeleted();
	}

	public Optional<Member> getMember(String memId) {
		return memberRepository.findByIdAndNotDeleted(memId);
	}

	public Optional<Member> findByMemNickname(String memNickname) {
		Optional<Member> opMember = memberRepository.findByMemNickname(memNickname);
		return opMember;
	}

	@Transactional
	public void insertMember(Member member) {
		member.setMemPassword(passwordEncoder.encode(member.getMemPassword()));
		member.setMemInsertAt(LocalDateTime.now()); // 가입 날짜
		member.setRoleCode(22L); // 회원
		member.setMemStatusCode(11L); // 정상 상태
		memberRepository.save(member);
	}

	public boolean isDuplicateId(String memId) {
		boolean idExists = getMember(memId).isPresent();
		return idExists;
	}

	public boolean isDuplicateNickname(String memNickname) {
		boolean nicknameExists = findByMemNickname(memNickname).isPresent();
		return nicknameExists;
	}

	@Transactional
	public Member updateMember(Member member) {
		Member findMember = getMember(member.getMemId()).get();
		findMember.setMemName(member.getMemName());
		findMember.setMemNickname(member.getMemNickname());
		findMember.setMemEmail(member.getMemEmail());
		findMember.setMemPhone(member.getMemPhone());
		return memberRepository.save(findMember);
	}

	@Transactional
	public void deleteMember(String memId) {
		Member findMember = getMember(memId).get();
		findMember.setMemDeleteAt(LocalDateTime.now()); // 탈퇴일
		findMember.setMemStatusCode(13L); // 탈퇴 회원
		memberRepository.save(findMember);
	}

}

package cookcloud.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cookcloud.entity.Member;
import cookcloud.repository.MemberRepository;
import cookcloud.service.MemberService;

import java.time.LocalDateTime;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 회원 가입 메서드
    public void registerMember(String memId, String memPassword, String memName, String memNickname, String memEmail, String memPhone) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memPassword);
        
        // 새로운 회원 객체 생성
        Member member = new Member();
        member.setMemId(memId);
        member.setMemPassword(encodedPassword);  // 암호화된 비밀번호 저장
        member.setMemName(memName);
        member.setMemNickname(memNickname);
        member.setMemEmail(memEmail);
        member.setMemPhone(memPhone);
        
        // 가입 날짜 및 기본값 설정
        member.setMemInsertAt(LocalDateTime.now());
        member.setRoleCode(22L);  // 기본값 (예: 일반 유저)
        member.setMemStatusCode(11L);  // 기본값 (예: 활성 상태)

        // DB에 저장
        memberRepository.save(member);
    }
}

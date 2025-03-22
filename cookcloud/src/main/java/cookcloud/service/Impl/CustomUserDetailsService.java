package cookcloud.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import cookcloud.entity.Member;
import cookcloud.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 🔥 여기에 @Autowired 써도 문제없음

    @Override
    public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(memId)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + memId));

        return User.builder()
            .username(member.getMemId())
            .password(member.getMemPassword())  // DB에 저장된 암호화된 비밀번호 사용
            .roles("USER")  // 기본 역할 설정 (실제 사용 안 해도 됨)
            .build();
    }
}

package cookcloud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cookcloud.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{

	Optional<Member> findByMemNickname(String memNickname);
	Optional<Member> findByMemId(String memId);
	
}

package cookcloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cookcloud.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String>{
	
	@Query("SELECT m FROM Member m WHERE m.memStatusCode IN (11, 12)")
	List<Member> findAllNotDeleted();

	@Query("SELECT m FROM Member m WHERE m.memId = :memId AND m.memStatusCode IN (11, 12)")
	Optional<Member> findByIdAndNotDeleted(@Param("memId") String memId);

	@Query("SELECT m FROM Member m WHERE m.memNickname = :memNickname AND m.memStatusCode IN (11, 12)")
	Optional<Member> findByMemNickname(String memNickname);
	
}
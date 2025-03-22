package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Member;
import cookcloud.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

	List<Message> findByMemberMemId(String memId);
	
}

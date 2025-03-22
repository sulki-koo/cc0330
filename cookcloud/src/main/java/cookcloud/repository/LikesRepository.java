package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Likes;
import cookcloud.entity.Member;
import cookcloud.entity.Recipe;

public interface LikesRepository extends JpaRepository<Likes, Long>{

	List<Likes> findByMemberMemId(String memId);
	
}

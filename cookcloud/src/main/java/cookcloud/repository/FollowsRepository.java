package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cookcloud.entity.Follows;
import cookcloud.entity.FollowsId;
import cookcloud.entity.Member;

@Repository
public interface FollowsRepository extends JpaRepository<Follows, FollowsId> {
	
    List<Follows> findByFollowerId(String memId);
    List<Follows> findByFollowingId(String memId);
	
}

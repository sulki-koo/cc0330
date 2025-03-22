package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

}

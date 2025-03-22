package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;

@Repository
public interface CodeRepository extends JpaRepository<Code, CodeId>{

}

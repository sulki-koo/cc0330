package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.RecipeLog;

public interface RecipeLogRepository extends JpaRepository<RecipeLog, Long>{

}

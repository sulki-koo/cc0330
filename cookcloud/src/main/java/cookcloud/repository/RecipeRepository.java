package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Member;
import cookcloud.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{

	List<Recipe> findByMemberMemId(String memId);
}

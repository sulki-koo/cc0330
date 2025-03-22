package cookcloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.entity.RecipeType;

public interface RecipeTypeRepository extends JpaRepository<RecipeType, Long>{

	
	
}

package cookcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cookcloud.entity.RecipeTag;
import cookcloud.entity.RecipeTagId;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, RecipeTagId>{

}

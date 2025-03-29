package cookcloud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cookcloud.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	
	@Query("SELECT r FROM Recipe r WHERE r.recipeIsDeleted = 'n' ORDER BY r.recipeId DESC")
	List<Recipe> findAllNotDeleted();
	
	@Query("SELECT r FROM Recipe r WHERE r.recipeId = :recipeId AND r.recipeIsDeleted = 'n'")
	Optional<Recipe> findByIdAndNotDeleted(@Param("recipeId") Long recipeId);

	@Query("SELECT r FROM Recipe r WHERE r.memId = :memId AND r.recipeIsDeleted = 'n' ORDER BY r.recipeId DESC")
	List<Recipe> findByMemId(@Param("memId") String memId);

	@Query("SELECT r FROM Recipe r WHERE r.recipeTitle LIKE %:keyword% OR r.memId LIKE %:keyword% AND r.recipeIsDeleted = 'n' ORDER BY r.recipeId DESC")
	List<Recipe> searchByKeyword(@Param("keyword") String keyword);

	// 레시피 유형 코드로 검색
	@Query("SELECT r FROM Recipe r WHERE r.recipeCode = :recipeCode AND r.recipeIsDeleted = 'n' ORDER BY r.recipeId DESC")
	List<Recipe> findByRecipeCode(@Param("recipeCode") Long recipeCode);

}

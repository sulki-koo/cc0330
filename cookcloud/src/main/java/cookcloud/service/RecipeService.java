package cookcloud.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cookcloud.entity.Hashtag;
import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.entity.RecipeTag;
import cookcloud.repository.HashtagRepository;
import cookcloud.repository.MemberRepository;
import cookcloud.repository.RecipeRepository;
import cookcloud.repository.RecipeTagRepository;

@Service
public class RecipeService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private RecipeTagRepository recipeTagRepository;

	@Autowired
	private HashtagRepository hashtagRepository;
	
	@Autowired
	private LikesService likesService;
	
	public List<Recipe> getRecipes() {
		return recipeRepository.findAllNotDeleted();
	}

	public Optional<Recipe> getRecipe(Long recipeId) {
		return recipeRepository.findByIdAndNotDeleted(recipeId);
	}
	
	// 개인 레시피 목록 조회
    public List<Recipe> getMyRecipes(String memId) {
    	List<Recipe> recipes = recipeRepository.findByMemId(memId);
        
        // 각 레시피에 첫 번째 첨부파일 URL을 설정
        for (Recipe recipe : recipes) {
            if (!recipe.getAttachList().isEmpty()) {
                recipe.setImageUrl(recipe.getAttachList().get(0).getAttachServerName());
            }
        }
        return recipes;
    }
    
	// memNickname을 기준으로 회원의 레시피 목록 조회
	public List<Recipe> getMemNicknameRecipes(String memNickname) {
		try {
			Member member = memberRepository.findAllNotDeleted().stream().filter(m -> m.getMemNickname().equals(memNickname))
					.findFirst().orElseThrow(() -> new IllegalAccessException("닉네임 " + memNickname + " 확인불가"));

			List<Recipe> recipes = recipeRepository.findAllNotDeleted().stream()
					.filter(recipe -> recipe.getMember().getMemId().equals(member.getMemId()))
					.collect(Collectors.toList());

			// 각 레시피에 첫 번째 첨부파일 URL 설정
			recipes.forEach(recipe -> {
				if (!recipe.getAttachList().isEmpty()) {
					recipe.setImageUrl(recipe.getAttachList().get(0).getAttachServerName());
				}
			});

			return recipes;
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
			return List.of();
		}
	}

	public List<Recipe> getLikedRecipes(String memId) {
	    List<Long> likedRecipeIds = likesService.getLikedRecipeIds(memId); // 좋아요한 recipeId 목록 가져오기
	    if (likedRecipeIds.isEmpty()) {
	        return Collections.emptyList(); // 좋아요한 레시피가 없으면 빈 리스트 반환
	    }
	    return recipeRepository.findRecipesByIds(likedRecipeIds);
	}

	// 키워드 검색
	public List<Recipe> searchRecipes(String keyword) {
		return recipeRepository.searchByKeyword(keyword);
	}

	// 레시피 유형으로 검색
	public List<Recipe> searchByRecipeType(Long recipeCode) {
		return recipeRepository.findByRecipeCode(recipeCode);
	}

	@Transactional
	public void createRecipe(Recipe recipe) {
		recipe.setRecipeViewCount(0L);
		recipe.setRecipeInsertAt(LocalDateTime.now());
		recipe.setRecipeIsDeleted("N");
		recipe.setRecipeBoardCode(41L);
		recipeRepository.save(recipe);
	}
	
	@Transactional
    public Recipe updateRecipe(Long recipeId, Recipe newRecipe) {
        return recipeRepository.findByIdAndNotDeleted(recipeId).map(recipe -> {
            recipe.setRecipeTitle(newRecipe.getRecipeTitle());
            recipe.setRecipeContent(newRecipe.getRecipeContent());
            recipe.setRecipeUpdateAt(LocalDateTime.now());
            return recipeRepository.save(recipe);
        }).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

	@Transactional
    public void deleteRecipe(Long recipeId) {
        recipeRepository.findByIdAndNotDeleted(recipeId).ifPresent(recipe -> {
            recipe.setRecipeIsDeleted("Y"); // isDeleted 값을 "Y"로 설정
            recipe.setRecipeDeleteAt(LocalDateTime.now()); // 삭제 시간 기록
            recipeRepository.save(recipe);
        });
    }

	public String getHashtagsForRecipe(Long recipeId) {
		// RecipeTag로부터 해당 레시피의 해시태그 아이디 목록을 조회
		List<RecipeTag> recipeTags = recipeTagRepository.findByRecipeId(recipeId);

		// 해시태그 아이디로 실제 해시태그명들을 가져오고 쉼표로 구분된 문자열로 반환
		return recipeTags.stream().map(recipeTag -> hashtagRepository.findById(recipeTag.getHashId()).orElse(null))
				.filter(hashtag -> hashtag != null).map(Hashtag::getHashName).collect(Collectors.joining(", "));
	}

}

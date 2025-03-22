package cookcloud.service;
import java.util.List;

import cookcloud.entity.Recipe;

public interface RecipeService {
	
	public abstract List<Recipe> getMemberRecipes(String memNickname); // 사용자 닉네임 클릭시 레시피 필터링
	
	

	
}

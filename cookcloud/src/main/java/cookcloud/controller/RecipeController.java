package cookcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cookcloud.entity.Recipe;
import cookcloud.service.RecipeService;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;
	
	@GetMapping("/member/{nickname}")
	public String getMemberRecipes(@PathVariable String nickname, Model model) {
		List<Recipe> recipes = recipeService.getMemberRecipes(nickname); // 서비스 계층에서 데이터를 가져옴
        model.addAttribute("recipes", recipes); // 레시피 목록을 모델에 추가
        model.addAttribute("nickname", nickname); // 닉네임을 모델에 추가
        return "recipeList"; // Thymeleaf 템플릿 이름 반환
    }
	
	
}

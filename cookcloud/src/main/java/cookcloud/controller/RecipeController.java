package cookcloud.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cookcloud.entity.Code;
import cookcloud.entity.CodeId;
import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.entity.Report;
import cookcloud.service.FollowsService;
import cookcloud.service.LikesService;
import cookcloud.service.MemberService;
import cookcloud.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private LikesService likesService;
	
	@Autowired
	private FollowsService followsService;

	public Map<CodeId, Code> getRecipeTypes(HttpServletRequest request) {
		return ((Map<CodeId, Code>) request.getServletContext().getAttribute("codeMap")).entrySet().stream()
				.filter(entry -> entry.getKey().getParentCode() == 5L)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // 필터링된 코드만 반환
	}

	public Map<CodeId, Code> getReportTypes(HttpServletRequest request) {
		return ((Map<CodeId, Code>) request.getServletContext().getAttribute("codeMap")).entrySet().stream()
				.filter(entry -> entry.getKey().getParentCode() == 7L)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@GetMapping
	public String getRecipes(Model model, HttpServletRequest request) {
		List<Recipe> recipes = recipeService.getRecipes();

		model.addAttribute("recipeTypes", getRecipeTypes(request));
		model.addAttribute("recipes", recipes);
		return "recipe/list";
	}

	@GetMapping("/recipe/{recipeId}")
	public String getRecipe(@PathVariable Long recipeId, Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
		Recipe recipe = recipeService.getRecipe(recipeId).get();
		Member member = memberService.getMember(recipe.getMemId()).get();

		// 로그인한 사용자 정보
	    String memId = (user != null) ? user.getUsername() : null;

	    // 현재 사용자가 이 레시피를 좋아요 했는지 확인
	    boolean liked = (memId != null) && 
                likesService.isLikedRecipeMemId(recipeId, memId)
                            .map(like -> "Y".equals(like.getLikeIsLiked()))
                            .orElse(false);
	    
	    // 현재 사용자가 이 레시피를 구독했는지 확인
	    boolean followed = (memId != null) && 
                followsService.isFollowing(recipe.getMemId(), memId)
                             .map(follow -> "Y".equals(follow.getFollowIsFollowing()))
                             .orElse(false);

		model.addAttribute("recipeTypes", getRecipeTypes(request));
		model.addAttribute("recipe", recipe);
		model.addAttribute("nickname", member.getMemNickname());
		model.addAttribute("liked", liked);
		model.addAttribute("followed", followed);
		model.addAttribute("reportTypes", getReportTypes(request));
		model.addAttribute("report", new Report());
		return "recipe/view";
	}

	@GetMapping("/search/{nickname}")
	public String getMemberRecipes(@PathVariable String nickname, Model model) {
		List<Recipe> recipes = recipeService.getMemNicknameRecipes(nickname);

		model.addAttribute("recipes", recipes);
		model.addAttribute("nickname", nickname);
		return "recipe/list";
	}

	@GetMapping("/search/{keyword}")
	public String searchRecipes(@PathVariable String keyword, Model model) {
		List<Recipe> recipes = recipeService.searchRecipes(keyword);

		model.addAttribute("recipes", recipes);
		return "recipe/list";
	}

	// 레시피 유형 가져오기 (동적으로 코드맵에서 가져오기)
	@GetMapping("/create")
	public String createRecipeForm(Model model, HttpServletRequest request) {
		model.addAttribute("recipeTypes", getRecipeTypes(request));
		return "recipe/create";
	}

	@PostMapping("/create")
	public String createRecipe(Recipe recipe, @RequestParam String memId, @RequestParam String recipeTitle,
			@RequestParam String recipeContent, @RequestParam String recipeCode) {

		System.out.println("sajdfkl;askldjfsfdlk;" + memId + recipeTitle + recipeContent + recipeCode);

		Long longRecipeCode = Long.parseLong(recipeCode);

		recipe.setRecipeTitle(recipeTitle);
		recipe.setRecipeContent(recipeContent);
		recipe.setRecipeCode(longRecipeCode);
		recipe.setMemId(memId);

		recipeService.createRecipe(recipe);
		return "redirect:/recipes";
	}

	@GetMapping("/update/{recipeId}")
	public String updateRecipeForm(@PathVariable Long recipeId, Model model, HttpServletRequest request) {
		Recipe recipe = recipeService.getRecipe(recipeId).get(); // 레시피 조회
		String hashtags = recipeService.getHashtagsForRecipe(recipeId); // 레시피에 대한 해시태그 조회

		model.addAttribute("recipe", recipe);
		model.addAttribute("recipeTypes", getRecipeTypes(request));
		model.addAttribute("hashtags", hashtags); // 쉼표로 구분된 해시태그 문자열 전달
		return "recipe/update"; // 수정 페이지로 이동
	}

	@PutMapping("/update/{recipeId}")
	public String updateRecipe(@PathVariable Long recipeId, Recipe recipe) {
		recipe.setRecipeTitle(recipe.getRecipeTitle());
		recipe.setRecipeContent(recipe.getRecipeContent());

		recipeService.updateRecipe(recipeId, recipe);
		return "recipe/view";
	}

	@PostMapping("/delete/{recipeId}")
	public String deleteRecipe(@PathVariable Long recipeId) {
		recipeService.deleteRecipe(recipeId);
		return "redirect:/recipes/list";
	}

}

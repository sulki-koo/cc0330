package cookcloud.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Member;
import cookcloud.entity.Recipe;
import cookcloud.repository.MemberRepository;
import cookcloud.repository.RecipeRepository;
import cookcloud.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public List<Recipe> getMemberRecipes(String memNickname) {
		try {
			Member member = memberRepository.findAll().stream()
					.filter(m -> m.getMemNickname().equals(memNickname)).findFirst()
					.orElseThrow(() -> new IllegalAccessException("닉네임 " + memNickname + " 확인불가"));

			 return recipeRepository.findAll().stream()
		                .filter(recipe -> recipe.getMember().getMemId().equals(member.getMemId()))  // id 비교
		                .collect(Collectors.toList());
		} catch (IllegalAccessException iae) {
			iae.printStackTrace();
		}
		return null;
	}

}

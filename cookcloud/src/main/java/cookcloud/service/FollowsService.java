package cookcloud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cookcloud.entity.Follows;
import cookcloud.entity.Likes;
import cookcloud.entity.Member;
import cookcloud.pubsub.PubSubServer;
import cookcloud.repository.FollowsRepository;

@Service
public class FollowsService {

	@Autowired
	private FollowsRepository followsRepository;
	
	public Optional<Follows> isFollowing(String followingId, String followerId) {
		return followsRepository.findByFollowingIdAndFollowerId(followingId, followerId);
	}

	// 내가 구독한(팔로잉) 사용자 목록 조회
	public List<Member> getMyFollowings(String memId) {
		return followsRepository.findByFollowerId(memId).stream().map(Follows::getFollowing)
				.collect(Collectors.toList());
	}

	// 나를 구독중인(팔로워) 사용자 목록 조회
	public List<Member> getMyFollowers(String memId) {
		return followsRepository.findByFollowingId(memId).stream().map(Follows::getFollower)
				.collect(Collectors.toList());
	}

	// 구독(팔로우) 처리 (토글) 및 즉시 알림 전송
	public boolean toggleFollow(String followingId, String followerId) {
		Optional<Follows> Follows = followsRepository.findByFollowingIdAndFollowerId(followingId, followerId);
		if (Follows.isPresent()) {
			Follows existingFollow = Follows.get();
			if ("Y".equals(existingFollow.getFollowIsFollowing())) {
				existingFollow.setFollowIsFollowing("N"); // 좋아요 취소
				followsRepository.save(existingFollow);
				return false;
			} else {
				existingFollow.setFollowIsFollowing("Y"); // 다시 좋아요
				followsRepository.save(existingFollow);
				return true;
			}
		} else {
			Follows follow = new Follows();
			follow.setFollowingId(followingId);
			follow.setFollowerId(followerId);
			follow.setFollowIsFollowing("Y");
			followsRepository.save(follow);
			return true;
		}
	}
	
}

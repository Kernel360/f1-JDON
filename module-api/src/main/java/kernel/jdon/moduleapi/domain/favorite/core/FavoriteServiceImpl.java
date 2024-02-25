package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.core.InflearnReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;
	private final MemberReader memberReader;
	private final InflearnReader inflearnReader;
	private final FavoriteInfoMapper favoriteInfoMapper;

	@Override
	@Transactional
	public FavoriteInfo.UpdateResponse save(Long memberId, Long lectureId) {
		Member findMember = memberReader.findById(memberId);
		InflearnCourse findInflearnCourse = inflearnReader.findById(lectureId);
		Favorite findFavorite = favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(findMember.getId(),
				findInflearnCourse.getId())
			.orElseGet(() -> saveNewFavorite(findMember, findInflearnCourse));
		Favorite saveFavorite = favoriteStore.save(findFavorite);

		return new FavoriteInfo.UpdateResponse(saveFavorite.getId());
	}

	private Favorite saveNewFavorite(Member member, InflearnCourse inflearnCourse) {
		Favorite favorite = new Favorite(member, inflearnCourse);

		return favoriteReader.save(favorite);
	}

	@Override
	@Transactional
	public FavoriteInfo.UpdateResponse delete(Long memberId, Long lectureId) {
		boolean memberExists = memberReader.existsById(memberId);
		if (!memberExists) {
			throw new ApiException(MemberErrorCode.NOT_FOUND_MEMBER);
		}
		Favorite findFavorite = favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId)
			.map(favoriteResponse -> favoriteReader.findById(favoriteResponse.getId())
				.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException))
			.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException);
		favoriteStore.delete(findFavorite);

		return new FavoriteInfo.UpdateResponse(findFavorite.getId());
	}

	@Override
	public FavoriteInfo.FindPageResponse getList(Long memberId, Pageable pageable) {
		Page<Favorite> favoritePage = favoriteReader.findList(memberId, pageable);
		Page<FavoriteInfo.FindResponse> infoPage = favoritePage.map(favoriteInfoMapper::of);

		return new FavoriteInfo.FindPageResponse(CustomPageResponse.of(infoPage));
	}
}

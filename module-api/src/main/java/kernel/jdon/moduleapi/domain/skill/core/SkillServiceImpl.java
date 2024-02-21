package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.skill.core.inflearnjd.InflearnJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.core.wantedjd.WantedJdSkillReader;
import kernel.jdon.skill.domain.SkillType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
	private final SkillReader skillReader;
	private final WantedJdSkillReader wantedJdSkillReader;
	private final InflearnJdSkillReader inflearnJdSkillReader;
	private final JobCategoryReader jobCategoryReader;

	@Override
	public SkillInfo.FindHotSkillListResponse getHotSkillList() {
		final List<SkillInfo.FindHotSkill> hotSkillList = skillReader.findHotSkillList();
		return new SkillInfo.FindHotSkillListResponse(hotSkillList);
	}

	@Override
	public SkillInfo.FindMemberSkillListResponse getMemberSkillList(final Long memberId) {
		final List<SkillInfo.FindMemberSkill> memberSkillList = skillReader.findMemberSkillList(memberId);
		return new SkillInfo.FindMemberSkillListResponse(memberSkillList);
	}

	@Override
	public SkillInfo.FindJobCategorySkillListResponse getJobCategorySkillList(final Long jobCategoryId) {
		final JobCategory findJobCategory = jobCategoryReader.findById(jobCategoryId);
		final List<SkillInfo.FindJobCategorySkill> jobCategorySkillList = findJobCategory.getSkillList().stream()
			.filter(skill -> !skill.getKeyword().equals(SkillType.getOrderKeyword()))
			.map(skill -> new SkillInfo.FindJobCategorySkill(skill.getId(), skill.getKeyword()))
			.toList();

		return new SkillInfo.FindJobCategorySkillListResponse(jobCategorySkillList);
	}

	@Override
	public SkillInfo.FindDataListBySkillResponse getDataListBySkill(String keyword, final Long memberId) {
		final String searchKeyword = getKeyword(keyword);
		final List<SkillInfo.FindJd> findJdList = wantedJdSkillReader.findWantedJdListBySkill(
			searchKeyword);
		final List<SkillInfo.FindLecture> findLectureList = inflearnJdSkillReader.findInflearnLectureListBySkill(
			searchKeyword, memberId);

		return new SkillInfo.FindDataListBySkillResponse(searchKeyword, findLectureList, findJdList);
	}

	private String getKeyword(final String keyword) {
		return Optional.ofNullable(keyword)
			.filter(StringUtils::hasText)
			.orElseGet(this::getHotSkillKeyword);
	}

	private String getHotSkillKeyword() {
		return skillReader.findHotSkillList().get(0).getKeyword();
	}
}

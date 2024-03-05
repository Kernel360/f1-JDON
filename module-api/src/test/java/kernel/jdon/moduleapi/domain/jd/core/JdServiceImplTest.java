package kernel.jdon.moduleapi.domain.jd.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

@DisplayName("Jd Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class JdServiceImplTest {
	@InjectMocks
	private JdServiceImpl jdServiceImpl;
	@Mock
	private JdReader jdReader;
	@Mock
	private JdInfoMapper jdInfoMapper;

	@Test
	@DisplayName("1: jdId가 주어졌을 때 getJd 메서드가 jd 상세정보와 스킬목록을 반환한다.")
	void givenValidJdId_whenGetJd_thenReturnCorrectJd() throws Exception {
		//given
		final var jdId = 1L;
		final var mockWantedJd = mock(WantedJd.class);
		final var mockSkillList = List.of(
			mock(JdInfo.FindSkill.class),
			mock(JdInfo.FindSkill.class));
		final var mockFindWantedInfo = mockFindWantedInfo(jdId, mockSkillList);

		//when
		when(jdReader.findWantedJd(jdId)).thenReturn(mockWantedJd);
		when(jdReader.findSkillListByWantedJd(mockWantedJd)).thenReturn(mockSkillList);
		when(jdInfoMapper.of(mockWantedJd, mockSkillList)).thenReturn(mockFindWantedInfo);
		final var response = jdServiceImpl.getJd(jdId);

		//then
		assertThat(response.getSkillList()).isEqualTo(mockSkillList);
		verify(jdReader, times(1)).findWantedJd(jdId);
		verify(jdReader, times(1)).findSkillListByWantedJd(mockWantedJd);
	}

	private JdInfo.FindWantedJdResponse mockFindWantedInfo(final Long jdId, final List<JdInfo.FindSkill> skillList) {
		return JdInfo.FindWantedJdResponse.builder()
			.id(jdId)
			.skillList(skillList)
			.build();
	}

	@Test
	@DisplayName("2: keyword가 주어졌을 때 getJdList가 jd 목록을 반환한다.")
	void givenValidKeyword_whenGetJdList_thenReturnCorrectJdList() throws Exception {
		//given
		final var keyword = "keyword";
		final var mockPageInfoRequest = mock(PageInfoRequest.class);
		final var mockFindWantedJdListInfo = mock(JdInfo.FindWantedJdListResponse.class);

		//when
		when(jdReader.findWantedJdList(mockPageInfoRequest, keyword)).thenReturn(mockFindWantedJdListInfo);
		final var response = jdServiceImpl.getJdList(mockPageInfoRequest, keyword);

		//then
		assertThat(response).isEqualTo(mockFindWantedJdListInfo);
		verify(jdReader, times(1)).findWantedJdList(mockPageInfoRequest, keyword);
	}
}
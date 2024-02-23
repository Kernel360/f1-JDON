package kernel.jdon.moduleapi.domain.coffeechat.core;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import kernel.jdon.moduleapi.domain.coffeechat.infrastructure.CoffeeChatReaderInfo;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatInfo {

	@Getter
	@Builder
	public static class FindResponse {
		private Long coffeeChatId;
		private Long hostId;
		private String nickname;
		private String job;
		private String title;
		private String content;
		private Long viewCount;
		private String status;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime meetDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime createdDate;
		private String openChatUrl;
		private Long totalRecruitCount;
		private Long currentRecruitCount;
	}

	@Getter
	public static class FindCoffeeChatListResponse {
		private List<FindCoffeeChatInfo> content;
		private CustomPageInfo pageInfo;

		public FindCoffeeChatListResponse(List<FindCoffeeChatInfo> content, CustomPageInfo pageInfo) {
			this.content = content;
			this.pageInfo = pageInfo;
		}
	}

	@Getter
	@Builder
	public static class FindCoffeeChatInfo {
		private Long coffeeChatId;
		private String nickname;
		private String job;
		private String title;
		private String activeStatus;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private Boolean isDeleted;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime meetDate;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private LocalDateTime createdDate;
		private Long totalRecruitCount;
		private Long currentRecruitCount;

		public static FindCoffeeChatInfo of(CoffeeChatReaderInfo.FindCoffeeChatListResponse readerInfo) {
			return FindCoffeeChatInfo.builder()
				.coffeeChatId(readerInfo.getCoffeeChatId())
				.nickname(readerInfo.getNickname())
				.job(readerInfo.getJob())
				.title(readerInfo.getJob())
				.activeStatus(readerInfo.getActiveStatus())
				.isDeleted(readerInfo.getIsDeleted())
				.meetDate(readerInfo.getMeetDate())
				.createdDate(readerInfo.getCreatedDate())
				.totalRecruitCount(readerInfo.getTotalRecruitCount())
				.currentRecruitCount(readerInfo.getCurrentRecruitCount())
				.build();
		}
	}
}

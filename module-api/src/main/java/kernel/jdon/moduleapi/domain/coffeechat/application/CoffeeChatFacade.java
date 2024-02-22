package kernel.jdon.moduleapi.domain.coffeechat.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatService;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoffeeChatFacade {

    private final CoffeeChatService coffeeChatService;

	public CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(
		final PageInfoRequest pageInfoRequest,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		return coffeeChatService.getCoffeeChatList(pageInfoRequest, command);
	}

	public CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId) {
		CoffeeChatInfo.FindResponse findResponse = coffeeChatService.getCoffeeChat(coffeeChatId);
		coffeeChatService.increaseViewCount(coffeeChatId);

        return findResponse;
    }

    public Long saveCoffeeChat(CoffeeChatCommand.CreateRequest request, Long memberId) {
        return coffeeChatService.createCoffeeChat(request, memberId);
    }

    public Long updateCoffeeChat(CoffeeChatCommand.UpdateRequest request, Long coffeeChatId) {
        return coffeeChatService.updateCoffeeChat(request, coffeeChatId);
    }

    public Long deleteCoffeeChat(Long coffeeChatId) {
        return coffeeChatService.deleteCoffeeChat(coffeeChatId);
    }
}

package kernel.jdon.moduleapi.domain.faq.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.faq.dto.request.CreateFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.request.ModifyFaqRequest;
import kernel.jdon.moduleapi.domain.faq.dto.response.CreateFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.DeleteFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.FindFaqResponse;
import kernel.jdon.moduleapi.domain.faq.dto.response.UpdateFaqResponse;
import kernel.jdon.moduleapi.domain.faq.service.FaqService;
import kernel.jdon.moduleapi.global.common.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FaqController {

	private final FaqService faqService;

	@GetMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<FindFaqResponse> get(@PathVariable(name = "faqId") Long faqId) {
		FindFaqResponse findFaqResponse = faqService.find(faqId);

		return ResponseEntity.ok().body(findFaqResponse);
	}

	@PostMapping("/api/v1/faqs")
	public ResponseEntity<CreateFaqResponse> save(@RequestBody CreateFaqRequest createFaqRequest) {
		CreateFaqResponse createFaqResponse = faqService.create(createFaqRequest);
		URI uri = URI.create("/api/v1/faqs/" + createFaqResponse.getId());

		return ResponseEntity.created(uri).body(createFaqResponse);
	}

	@DeleteMapping("/api/v1/faqs/{faqId}")
	public ResponseEntity<CommonResponse> remove(@PathVariable(name = "faqId") Long faqId) {
		DeleteFaqResponse deleteFaqResponse = faqService.delete(faqId);

		return ResponseEntity.ok(CommonResponse.of(deleteFaqResponse));
	}

	@PutMapping("/api/v1/faqs")
	public ResponseEntity<CommonResponse> modify(@RequestBody ModifyFaqRequest modifyFaqRequest) {
		UpdateFaqResponse updateFaqResponse = faqService.update(modifyFaqRequest);

		return ResponseEntity.ok().body(CommonResponse.of(updateFaqResponse));
	}
}

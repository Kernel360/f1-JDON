package kernel.jdon.moduleapi.global.config.auth;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kernel.jdon.auth.util.RedirectUrlUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JdonLogoutSuccessHandler implements LogoutSuccessHandler {
	private final RedirectUrlUtil redirectUrlUtil;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {
		new CookieClearingLogoutHandler("JSESSIONID");
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect(redirectUrlUtil.getLogoutSuccess(request.getHeader("Referer")));
	}
}

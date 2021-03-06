package br.com.mstec.aw.algamoney.api.token;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import br.com.mstec.aw.algamoney.api.config.property.AlgamoneyApiProperty;

@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty; 
	
	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		
		HttpServletRequest httpServletRequest = ((ServletServerHttpRequest)request).getServletRequest();
		HttpServletResponse httpServletResponse = ((ServletServerHttpResponse)request).getServletResponse();
		
		String refreshToken = body.getRefreshToken().getValue();
		
		adicionarRefreshTokenNoCookie(refreshToken, httpServletRequest, httpServletResponse);
		
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		removerRefreshTokenDoBody(token);
		
		return body;
	}

	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		Cookie cookie = new Cookie("refreshToken", refreshToken);
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiProperty.getSecurity().isEnableHttps());
		cookie.setPath(httpServletRequest.getContextPath() + "/oauth/token");
		cookie.setMaxAge(24*60*60);
		httpServletResponse.addCookie(cookie);
		
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken");
	}

	
}

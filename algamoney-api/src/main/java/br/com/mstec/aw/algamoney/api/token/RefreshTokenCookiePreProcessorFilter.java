package br.com.mstec.aw.algamoney.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		
		if("oauth/token".equalsIgnoreCase(httpServletRequest.getRequestURI())
			&& "refresh_token".equals(httpServletRequest.getParameter("grant_type"))
			&& httpServletRequest.getCookies() != null) {
			
			for(Cookie cookie : httpServletRequest.getCookies()) {
				if(cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					httpServletRequest = (HttpServletRequest) new MyServletRequestWrapper(httpServletRequest, refreshToken);
				}
			}
		}
		
		filterChain.doFilter(httpServletRequest, servletResponse);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}
	
	class MyServletRequestWrapper extends ServletRequestWrapper {

		private String refreshToken;

		public MyServletRequestWrapper(ServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> parameterMap = new ParameterMap<>(getRequest().getParameterMap());
			parameterMap.put("refresh_token", new String[] { refreshToken });
			return parameterMap;
		}

		public String getRefreshToken() {
			return refreshToken;
		}

		public void setRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
		}
		
	}

}

package br.com.mstec.aw.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import br.com.mstec.aw.algamoney.api.config.property.AlgamoneyApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		httpServletResponse.setHeader("Access-Control-Allow-Origin", algamoneyApiProperty.getSecurity().getCorsAllowedOrigin());
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		
		if(HttpMethod.OPTIONS.toString().equals(httpServletRequest.getMethod())
				&& algamoneyApiProperty.getSecurity().getCorsAllowedOrigin().equals(httpServletRequest.getHeader("Origin"))) {
			httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			httpServletResponse.setHeader("Access-Control-Max-Age", "3600");
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		}
		else {
			filterChain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}

package br.com.mstec.aw.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class RecursoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 6334232184650516719L;
	
	private HttpServletResponse httpServletResponse;
	private Long codigo;
	
	public RecursoCriadoEvent(Object source, HttpServletResponse httpServletResponse, Long codigo) {
		super(source);
		this.httpServletResponse = httpServletResponse;
		this.codigo = codigo;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	public Long getCodigo() {
		return codigo;
	}

}

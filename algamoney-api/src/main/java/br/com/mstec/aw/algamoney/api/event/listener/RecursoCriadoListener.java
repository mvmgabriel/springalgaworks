package br.com.mstec.aw.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mstec.aw.algamoney.api.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse httpServletResponse = recursoCriadoEvent.getHttpServletResponse();
		Long codigo = recursoCriadoEvent.getCodigo();
		
		adicionarLocation(httpServletResponse, codigo);
	}

	private void adicionarLocation(HttpServletResponse httpServletResponse, Long codigo) {
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(codigo).toUri();
		httpServletResponse.addHeader("Location", uriLocation.toASCIIString());
	}
	
}

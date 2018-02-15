package br.com.mstec.aw.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mstec.aw.algamoney.api.model.Lancamento;
import br.com.mstec.aw.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}

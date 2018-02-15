package br.com.mstec.aw.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mstec.aw.algamoney.api.model.Lancamento;
import br.com.mstec.aw.algamoney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}

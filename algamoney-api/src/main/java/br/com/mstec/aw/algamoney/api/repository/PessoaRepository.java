package br.com.mstec.aw.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mstec.aw.algamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}

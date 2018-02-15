package br.com.mstec.aw.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mstec.aw.algamoney.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}

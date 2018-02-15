package br.com.mstec.aw.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.mstec.aw.algamoney.api.model.Lancamento;
import br.com.mstec.aw.algamoney.api.model.Lancamento_;
import br.com.mstec.aw.algamoney.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder =  entityManager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteriaQuery = criteriaBuilder.createQuery(Lancamento.class);

		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		
		TypedQuery<Lancamento> query = entityManager.createQuery(criteriaQuery);
		adicionarParametrosPaginacao(query, pageable);
		
		return new PageImpl<Lancamento>(query.getResultList(), pageable, countFilter(lancamentoFilter));
	}

	private void adicionarParametrosPaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int registrosPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual*registrosPorPagina;
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(registrosPorPagina);
	}

	private long countFilter(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder criteriaBuilder =  entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

		Root<Lancamento> root = criteriaQuery.from(Lancamento.class);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, criteriaBuilder, root);
		criteriaQuery.where(predicates);
		criteriaQuery.select(criteriaBuilder.count(root));
		
		return entityManager.createQuery(criteriaQuery).getSingleResult();
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder criteriaBuilder,
			Root<Lancamento> root) {

		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(criteriaBuilder.like(
					criteriaBuilder.lower(root.get(Lancamento_.descricao)), 
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), 
					lancamentoFilter.getDataVencimentoDe()));
		}
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(
					root.get(Lancamento_.dataVencimento), 
					lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}

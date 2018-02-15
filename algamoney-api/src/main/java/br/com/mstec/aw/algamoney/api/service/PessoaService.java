package br.com.mstec.aw.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.mstec.aw.algamoney.api.model.Pessoa;
import br.com.mstec.aw.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaDB = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaDB, "codigo");
		return pessoaRepository.save(pessoaDB); 
	}

	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaDB = pessoaRepository.findOne(codigo);
		if(pessoaDB == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaDB;
	}

	public Pessoa atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaDB = buscarPessoaPeloCodigo(codigo);
		pessoaDB.setAtivo(ativo);
		return pessoaRepository.save(pessoaDB); 
	}
	
	public void setPessoaRepository(PessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
}

package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ITeatroDAO;
import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.service.spec.ITeatroService;

@Service
@Transactional(readOnly = false)
public class TeatroService implements ITeatroService {

	@Autowired
	ITeatroDAO dao;
	
	public void salvar(Teatro teatro) {
		dao.save(teatro);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Teatro buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Teatro> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public List<Teatro> buscarTodos(String cidade) {
		return dao.findAllByCidade(cidade);
	}

	@Transactional(readOnly = true)
	public boolean teatroTemPromos(Long id) {
		return !dao.findById(id.longValue()).getPromos().isEmpty(); 
	}
}

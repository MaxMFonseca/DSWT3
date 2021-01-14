package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPromoDAO;
import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.service.spec.IPromoService;

@Service
@Transactional(readOnly = false)
public class PromoService implements IPromoService {

	@Autowired
	IPromoDAO dao;
	
	public void salvar(Promo promo) {
		dao.save(promo);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Promo buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Promo> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Promo> buscarTodos(Teatro t) {
		return dao.findAllByTeatro(t);
	}

	@Transactional(readOnly = true)
	public List<Promo> buscarTodos(Site s) {
		return dao.findAllBySite(s);
	}


	@Transactional(readOnly = true)
	public List<Promo> buscarTodosPorTempo(String t) {
		return dao.findAllByTime(t);
	}
}

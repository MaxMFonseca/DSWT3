package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ISiteDAO;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.service.spec.ISiteService;

@Service
@Transactional(readOnly = false)
public class SiteService implements ISiteService {

	@Autowired
	ISiteDAO dao;
	
	public void salvar(Site site) {
		dao.save(site);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Site buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Site> buscarTodos() {
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public boolean siteTemPromos(Long id) {
		return !dao.findById(id.longValue()).getPromos().isEmpty(); 
	}
}

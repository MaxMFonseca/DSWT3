package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Site;

@SuppressWarnings("unchecked")
public interface ISiteDAO extends CrudRepository<Site, Long>{

	Site findById(long id);

	List<Site> findAll();
	
	Site save(Site site);

	void deleteById(Long id);
}
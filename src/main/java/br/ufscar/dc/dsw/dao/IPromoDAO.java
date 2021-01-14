package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.domain.Teatro;

@SuppressWarnings("unchecked")
public interface IPromoDAO extends CrudRepository<Promo, Long>{

	Promo findById(long id);

	List<Promo> findAll();
	
	List<Promo> findAllBySite(Site s);

	List<Promo> findAllByTeatro(Teatro t);

	List<Promo> findAllByTime(String t);

	Promo save(Promo promo);

	void deleteById(Long id);
}
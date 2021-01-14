package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Teatro;

@SuppressWarnings("unchecked")
public interface ITeatroDAO extends CrudRepository<Teatro, Long>{

	Teatro findById(long id);

	List<Teatro> findAll();

	List<Teatro> findAllByCidade(String cidade);
	
	Teatro save(Teatro teatro);

	void deleteById(Long id);
}
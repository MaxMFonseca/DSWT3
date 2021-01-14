package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Site;

public interface ISiteService {

	Site buscarPorId(Long id);

	List<Site> buscarTodos();

	void salvar(Site teatro);

	void excluir(Long id);
	
	boolean siteTemPromos(Long id);
}

package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.domain.Teatro;

public interface IPromoService {

	Promo buscarPorId(Long id);
	
	List<Promo> buscarTodos();
	
	public List<Promo> buscarTodos(Site s);

	public List<Promo> buscarTodos(Teatro t);

	List<Promo> buscarTodosPorTempo(String t);

	void salvar(Promo promo);
	
	void excluir(Long id);
	
	public long ultimoID();
}

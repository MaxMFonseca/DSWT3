package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Teatro;

public interface ITeatroService {

	Teatro buscarPorId(Long id);

	List<Teatro> buscarTodos();

	public List<Teatro> buscarTodos(String cidade);

	void salvar(Teatro teatro);

	void excluir(Long id);
	
	boolean teatroTemPromos(Long id);
}

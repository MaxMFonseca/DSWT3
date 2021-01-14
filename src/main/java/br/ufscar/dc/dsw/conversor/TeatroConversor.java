package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.service.spec.ITeatroService;

@Component
public class TeatroConversor implements Converter<String, Teatro>{

	@Autowired
	private ITeatroService service;
	
	@Override
	public Teatro convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}

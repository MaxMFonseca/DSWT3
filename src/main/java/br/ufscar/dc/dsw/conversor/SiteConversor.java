package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.service.spec.ISiteService;

@Component
public class SiteConversor implements Converter<String, Site>{

	@Autowired
	private ISiteService service;
	
	@Override
	public Site convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}

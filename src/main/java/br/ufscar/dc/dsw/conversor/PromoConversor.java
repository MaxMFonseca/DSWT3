package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.service.spec.IPromoService;

@Component
public class PromoConversor implements Converter<String, Promo>{

	@Autowired
	private IPromoService service;
	
	@Override
	public Promo convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}

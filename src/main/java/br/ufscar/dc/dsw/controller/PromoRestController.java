package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.service.spec.IPromoService;
import br.ufscar.dc.dsw.service.spec.ISiteService;
import br.ufscar.dc.dsw.service.spec.ITeatroService;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PromoRestController {

	@Autowired
	private IPromoService promoService;

	@Autowired
	private ITeatroService teatroService;

	@Autowired
	private ISiteService siteService;
    
	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Promo promo, JSONObject json) {

		if(promo.getId() == null){
			Object id = json.get("id");
			if (id != null) {
				if (id instanceof Integer) {
					promo.setId(((Integer) id).longValue());
				} else {
					promo.setId(((Long) id));
				}
			}else
				promo.setId(promoService.ultimoID() + 1);
		}

		promo.setNomePeca((String) json.get("nomePeca"));
		promo.setTime((String) json.get("time"));
		Double value = (Double) json.get("preco");
		promo.setPreco(BigDecimal.valueOf(value));
        
		Teatro teatro = new Teatro();
		Object teatroid = json.get("teatroid");

		if (teatroid != null) {
			if (teatroid instanceof Integer) {
				teatro = teatroService.buscarPorId(((Integer) teatroid).longValue());
			} else {
				teatro = teatroService.buscarPorId(((Long) teatroid));
			}
		}
		System.out.println(teatro.getNome());
		promo.setTeatro(teatro);

		Site site = new Site();
		Object siteid = json.get("siteid");
		
		if (siteid != null) {
			if (siteid instanceof Integer) {
				site = siteService.buscarPorId(((Integer) siteid).longValue());
			} else {
				site = siteService.buscarPorId(((Long) siteid));
			}
		}
		System.out.println(site.getNome());
		promo.setSite(site);
	}

	@GetMapping(path = "/promocoes")
	public ResponseEntity<List<Promo>> lista() {
		List<Promo> lista = promoService.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/promocoes/{id}")
	public ResponseEntity<Promo> lista(@PathVariable("id") long id) {
		Promo promo = promoService.buscarPorId(id);
		if (promo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(promo);
	}
	
	@PostMapping(path = "/promocoes")
	@ResponseBody
	public ResponseEntity<Promo> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Promo promo = new Promo();
				parse(promo, json);
				promoService.salvar(promo);
				return ResponseEntity.ok(promo);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

    @GetMapping(path = "/promocoes/sites/{id}")
	public ResponseEntity<List<Promo>> listaPorSite(@PathVariable("id") Long id) {
		List<Promo> lista = promoService.buscarTodos(siteService.buscarPorId(id));
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
    }
    
    @GetMapping(path = "/promocoes/teatros/{id}")
	public ResponseEntity<List<Promo>> listaPorTeatro(@PathVariable("id") Long id) {
		List<Promo> lista = promoService.buscarTodos(teatroService.buscarPorId(id));
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@PutMapping(path = "/promocoes/{id}")
	public ResponseEntity<Promo> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Promo promo = promoService.buscarPorId(id);
				if (promo == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(promo, json);
					promoService.salvar(promo);
					return ResponseEntity.ok(promo);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/promocoes/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Promo promo = promoService.buscarPorId(id);
		if (promo == null) {
			return ResponseEntity.notFound().build();
		} else {
			promoService.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}
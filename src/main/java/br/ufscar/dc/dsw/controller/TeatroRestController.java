package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.service.spec.IPromoService;
import br.ufscar.dc.dsw.service.spec.ITeatroService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TeatroRestController {

	@Autowired
	private ITeatroService service;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPromoService pService;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Teatro teatro, JSONObject json) {
		if(teatro.getId() == null){
			Object id = json.get("id");
			if (id != null) {
				if (id instanceof Integer) {
					teatro.setId(((Integer) id).longValue());
				} else {
					teatro.setId(((Long) id));
				}
			}else
			teatro.setId(usuarioService.ultimoID() + 1);
		}

		teatro.setCNPJ((String) json.get("cnpj"));
		teatro.setNome((String) json.get("nome"));
		teatro.setCidade((String) json.get("cidade"));
		
		teatro.setUsername((String) json.get("username"));
		teatro.setPassword(encoder.encode((String)json.get("password")));
		teatro.setRole((String) json.get("role"));	
	}

	@GetMapping(path = "/teatros")
	public ResponseEntity<List<Teatro>> lista() {
		List<Teatro> lista = service.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/teatros/{id}")
	public ResponseEntity<Teatro> lista(@PathVariable("id") long id) {
		Teatro teatro = service.buscarPorId(id);
		if (teatro == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(teatro);
	}

	@PostMapping(path = "/teatros")
	@ResponseBody
	public ResponseEntity<Teatro> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Teatro teatro = new Teatro();
				parse(teatro, json);
				service.salvar(teatro);
				return ResponseEntity.ok(teatro);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@GetMapping(path = "/teatros/cidades/{cidade}")
	public ResponseEntity<List<Teatro>> listaPorCidade(@PathVariable("cidade") String cidade) {
		List<Teatro> lista = service.buscarTodos(cidade);
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}

	@PutMapping(path = "/teatros/{id}")
	public ResponseEntity<Teatro> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Teatro teatro = service.buscarPorId(id);
				if (teatro == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(teatro, json);
					service.salvar(teatro);
					return ResponseEntity.ok(teatro);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/teatros/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {	
		Teatro teatro = service.buscarPorId(id);
		if (teatro == null) {
			return ResponseEntity.notFound().build();
		} else {
			List<Promo> promos = pService.buscarTodos(service.buscarPorId(id));
			for (Promo promo : promos) {
				pService.excluir(promo.getId());
			}

			service.excluir(id);
			return ResponseEntity.noContent().build();
		}
	}
}

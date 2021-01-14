package br.ufscar.dc.dsw.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.service.spec.IPromoService;
import br.ufscar.dc.dsw.service.spec.ITeatroService;

@Controller
@RequestMapping("/teatros")
public class TeatroController {
	
	@Autowired
	private ITeatroService service;
	
	@Autowired
	private IPromoService pService;

	@GetMapping("/cadastrar")
	public String cadastrar(Teatro teatro) {
		return "teatro/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		List<Teatro> teatros = service.buscarTodos();
		Set<String> cidades = new HashSet<String>();
		for (Teatro teatro : teatros) {
			cidades.add(teatro.getCidade());
		}
		model.addAttribute("teatros", teatros);
		model.addAttribute("cidades", cidades);
		return "teatro/lista";
	}

	@GetMapping("/listar/{cidade}")
	public String listarPorCidade(@PathVariable("cidade") String cidade, ModelMap model) {
		model.addAttribute("teatros", service.buscarTodos(cidade));
		return "teatro/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Teatro teatro, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "teatro/cadastro";
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		teatro.setPassword(encoder.encode(teatro.getPassword()));
		service.salvar(teatro);
		attr.addFlashAttribute("sucess", "Teatro inserido com sucesso.");
		return "redirect:/teatros/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("teatro", service.buscarPorId(id));
		return "teatro/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Teatro teatro, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "teatro/cadastro";
		}

		service.salvar(teatro);
		attr.addFlashAttribute("sucess", "Teatro editado com sucesso.");
		return "redirect:/teatros/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		List<Promo> promos = pService.buscarTodos(service.buscarPorId(id));
		for (Promo promo : promos) {
			pService.excluir(promo.getId());
		}
			
		service.excluir(id);
		model.addAttribute("sucess", "Teatro excluído com sucesso.");
		return listar(model);
	}
}

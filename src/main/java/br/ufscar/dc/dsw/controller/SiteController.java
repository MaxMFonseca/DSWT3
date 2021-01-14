package br.ufscar.dc.dsw.controller;

import java.util.List;

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
import br.ufscar.dc.dsw.domain.Site;

import br.ufscar.dc.dsw.service.spec.IPromoService;
import br.ufscar.dc.dsw.service.spec.ISiteService;

@Controller
@RequestMapping("/sites")
public class SiteController {
	
	@Autowired
	private ISiteService service;

	@Autowired
	private IPromoService pService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Site site) {
		return "site/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("sites",service.buscarTodos());
		return "site/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Site site, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "site/cadastro";
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		site.setPassword(encoder.encode(site.getPassword()));
		
		service.salvar(site);
		attr.addFlashAttribute("sucess", "Site inserido com sucesso.");
		return "redirect:/sites/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("site", service.buscarPorId(id));
		return "site/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Site site, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "site/cadastro";
		}

		service.salvar(site);
		attr.addFlashAttribute("sucess", "Site editado com sucesso.");
		return "redirect:/sites/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		
		List<Promo> promos = pService.buscarTodos(service.buscarPorId(id));
		for (Promo promo : promos) {
			pService.excluir(promo.getId());
		}
			
		service.excluir(id);
		model.addAttribute("sucess", "Site excluído com sucesso.");
		
		return listar(model);
	}
}

package br.ufscar.dc.dsw.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.service.spec.ITeatroService;
import br.ufscar.dc.dsw.service.spec.ISiteService;
import br.ufscar.dc.dsw.service.spec.IPromoService;

@Controller
@RequestMapping("/promos")
public class PromoController {

	@Autowired
	private IPromoService promoService;

	@Autowired
	private ITeatroService teatroService;

	@Autowired
	private ISiteService siteService;

	@GetMapping("/cadastrar")
	public String cadastrar(Promo promo) {
		return "promo/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("promos", promoService.buscarTodos());
		return "promo/lista";
	}

	@GetMapping("/listarIndvTeatro")
	public String listarIndvPorTeatro(ModelMap model) {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Teatro t = teatroService.buscarPorId(usuarioDetails.getUsuario().getId());
		model.addAttribute("promos", promoService.buscarTodos(t));
		return "promo/lista";
	}

	@GetMapping("/listarIndvSite")
	public String listarIndvPorSite(ModelMap model) {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Site s = siteService.buscarPorId(usuarioDetails.getUsuario().getId());
		model.addAttribute("promos", promoService.buscarTodos(s));
		return "promo/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Promo promo, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "redirect:/promos/cadastro";
		}

		promo.setTime(promo.getTime().replace('T', ' '));

		List<Promo> tempit = promoService.buscarTodosPorTempo(promo.getTime());
		for (Promo p2 : tempit) {
			if (p2.getTeatro().getId() == promo.getTeatro().getId() && p2.getSite().getId() == promo.getSite().getId()) {
				attr.addFlashAttribute("fail", "Promo não criada. Combo Site/Teatro/Tempo já existem!");
				return "redirect:/promos/listarIndvTeatro";
			}
		}

		promoService.salvar(promo);
		attr.addFlashAttribute("sucess", "Promo inserida com sucesso");
		return "redirect:/promos/listarIndvTeatro";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("promo", promoService.buscarPorId(id));
		return "promo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Promo promo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "promo/cadastro";
		}

		promo.setTime(promo.getTime().replace('T', ' '));

		List<Promo> tempit = promoService.buscarTodosPorTempo(promo.getTime());
		for (Promo p2 : tempit) {
			if (p2.getTeatro().getId() == promo.getTeatro().getId() && p2.getSite().getId() == promo.getSite().getId()) {
				attr.addFlashAttribute("fail", "Promo não editada. Combo Site/Teatro/Tempo já existem!");
				return "redirect:/promos/listarIndvTeatro";
			}
		}

		promoService.salvar(promo);
		attr.addFlashAttribute("sucess", "Promo editada com sucesso.");
		return "redirect:/promos/listarIndvTeatro";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		promoService.excluir(id);
		attr.addFlashAttribute("sucess", "Promo excluída com sucesso.");
		return "redirect:/promos/listar";
	}

	@ModelAttribute("usuario")
	public Teatro obterUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return teatroService.buscarPorId(usuarioDetails.getUsuario().getId());
	}

	@ModelAttribute("teatros")
	public List<Teatro> listaTeatros() {
		return teatroService.buscarTodos();
	}

	@ModelAttribute("sites")
	public List<Site> listaSites() {
		return siteService.buscarTodos();
	}
}

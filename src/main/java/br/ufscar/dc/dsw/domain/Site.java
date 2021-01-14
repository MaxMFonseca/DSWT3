package br.ufscar.dc.dsw.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table
public class Site extends Usuario {

	@NotBlank
	@Size(min = 3, max = 60, message = "{Size.site.url}")
	@Column(nullable = false, unique = true, length = 60)
	private String url;
	
	@NotBlank
	@Size(min = 3, max = 60)
	@Column(nullable = false, unique = true, length = 60)
	private String nome;

	@NotBlank
	@Size(min = 11, max = 11)
	@Column(nullable = false, unique = false, length = 11)
	private String telefone;

	@OneToMany
	private List<Promo> promos;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<Promo> getPromos() {
		return promos;
	}

	public void setPromos(List<Promo> promos) {
		this.promos = promos;
	}
}

package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table
public class Promo extends AbstractEntity<Long> {

	@NotBlank(message = "{NotBlank.promo.nomePeca}")
	@Size(max = 60)
	@Column(nullable = false, length = 60)
	private String nomePeca;
	
	@NotNull(message = "{NotNull.promo.preco}")
	@Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal preco;

	@NotNull(message = "{NotNull.promo.time}")
	@Column(nullable = false)
	private String time;
	
	@NotNull(message = "{NotNull.promo.teatro}")
	@ManyToOne
	@JoinColumn(name = "teatro_id")
	private Teatro teatro;

	@NotNull(message = "{NotNull.promo.site}")
	@ManyToOne
	@JoinColumn(name = "site_id")
	private Site site;

	public String getNomePeca() {
		return nomePeca;
	}

	public void setNomePeca(String nomePeca) {
		this.nomePeca = nomePeca;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}	

	public Teatro getTeatro() {
		return teatro;
	}

	public void setTeatro(Teatro teatro) {
		this.teatro = teatro;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}

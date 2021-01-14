package br.ufscar.dc.dsw;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IPromoDAO;
import br.ufscar.dc.dsw.dao.ISiteDAO;
import br.ufscar.dc.dsw.dao.ITeatroDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Promo;
import br.ufscar.dc.dsw.domain.Site;
import br.ufscar.dc.dsw.domain.Teatro;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class SiteTeatroPromoMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SiteTeatroPromoMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, BCryptPasswordEncoder encoder, ITeatroDAO teatroDAO, ISiteDAO siteDAO, IPromoDAO promoDAO) {
		return (args) -> {
			
			Usuario u1 = new Usuario();
			u1.setUsername("admin@trab.com");
			u1.setPassword(encoder.encode("admin"));
			u1.setRole("ROLE_ADMIN");
			u1.setEnabled(true);
			usuarioDAO.save(u1);
			
			Teatro t1 = new Teatro();
			t1.setCNPJ("61.256.320/0001-58");
			t1.setNome("Teatro1");
			t1.setCidade("Cidade1");
			
			t1.setUsername("teatro1@trab.com");
			t1.setPassword(encoder.encode("teatro1"));
			t1.setRole("ROLE_TTR");
			t1.setEnabled(true);
			teatroDAO.save(t1);

			Teatro t2 = new Teatro();
			t2.setCNPJ("11.451.220/0001-58");
			t2.setNome("Teatro2");
			t2.setCidade("Cidade2");
			
			t2.setUsername("teatro2@trab.com");
			t2.setPassword(encoder.encode("teatro2"));
			t2.setRole("ROLE_TTR");
			t2.setEnabled(true);
			teatroDAO.save(t2);

			Teatro t3 = new Teatro();
			t3.setCNPJ("78.256.320/0001-58");
			t3.setNome("Teatro3");
			t3.setCidade("Cidade3");
			
			t3.setUsername("teatro3@trab.com");
			t3.setPassword(encoder.encode("teatro3"));
			t3.setRole("ROLE_TTR");
			t3.setEnabled(true);
			teatroDAO.save(t3);

			Teatro t4 = new Teatro();
			t4.setCNPJ("22.451.220/0001-58");
			t4.setNome("Teatro4");
			t4.setCidade("Cidade1");
			
			t4.setUsername("teatro4@trab.com");
			t4.setPassword(encoder.encode("teatro4"));
			t4.setRole("ROLE_TTR");
			t4.setEnabled(true);
			teatroDAO.save(t4);

			Site s1 = new Site();
			s1.setUrl("www.site1.com");
			s1.setNome("Site1");
			s1.setTelefone("12345678911");
			
			s1.setUsername("site1@trab.com");
			s1.setPassword(encoder.encode("site1"));
			s1.setRole("ROLE_SITE");
			s1.setEnabled(true);
			siteDAO.save(s1);

			Site s2 = new Site();
			s2.setUrl("www.site2.com");
			s2.setNome("Site2");
			s2.setTelefone("12345678951");
			
			s2.setUsername("site2@trab.com");
			s2.setPassword(encoder.encode("site2"));
			s2.setRole("ROLE_SITE");
			s2.setEnabled(true);
			siteDAO.save(s2);

			Site s3 = new Site();
			s3.setUrl("www.site3.com");
			s3.setNome("Site3");
			s3.setTelefone("12355623651");
			
			s3.setUsername("site3@trab.com");
			s3.setPassword(encoder.encode("site3"));
			s3.setRole("ROLE_SITE");
			s3.setEnabled(true);
			siteDAO.save(s3);

			Site s4 = new Site();
			s4.setUrl("www.site4.com");
			s4.setNome("Site4");
			s4.setTelefone("12354278951");
			
			s4.setUsername("site4@trab.com");
			s4.setPassword(encoder.encode("site4"));
			s4.setRole("ROLE_SITE");
			s4.setEnabled(true);
			siteDAO.save(s4);

			Site s5 = new Site();
			s5.setUrl("www.site5.com");
			s5.setNome("SiteV2");
			s5.setTelefone("12351538951");
			
			s5.setUsername("site5@trab.com");
			s5.setPassword(encoder.encode("site5"));
			s5.setRole("ROLE_SITE");
			s5.setEnabled(true);
			siteDAO.save(s5);

			Promo p1 = new Promo();
			p1.setNomePeca("Peca1");
			p1.setPreco(new BigDecimal(45.90));
			p1.setTime("2021-01-23 20:30");
			p1.setSite(s1);
			p1.setTeatro(t1);
			promoDAO.save(p1);

			Promo p2 = new Promo();
			p2.setNomePeca("Peca2");
			p2.setPreco(new BigDecimal(30.30));
			p2.setTime("2025-09-23 03:50");
			p2.setSite(s1);
			p2.setTeatro(t1);
			promoDAO.save(p2);

			Promo p3 = new Promo();
			p3.setNomePeca("Peca3");
			p3.setPreco(new BigDecimal(20.00));
			p3.setTime("2021-09-22 13:00");
			p3.setSite(s2);
			p3.setTeatro(t2);
			promoDAO.save(p3);

			Promo p4 = new Promo();
			p4.setNomePeca("Peca4");
			p4.setPreco(new BigDecimal(13.10));
			p4.setTime("2021-09-23 22:00");
			p4.setSite(s2);
			p4.setTeatro(t3);
			promoDAO.save(p4);

			Promo p5 = new Promo();
			p5.setNomePeca("Peca5");
			p5.setPreco(new BigDecimal(24.50));
			p5.setTime("2021-10-23 15:00");
			p5.setSite(s2);
			p5.setTeatro(t4);
			promoDAO.save(p5);

			Promo p6 = new Promo();
			p6.setNomePeca("Peca6");
			p6.setPreco(new BigDecimal(14.12));
			p6.setTime("1999-09-01 13:00");
			p6.setSite(s2);
			p6.setTeatro(t2);
			promoDAO.save(p6);

			Promo p7 = new Promo();
			p7.setNomePeca("Peca4");
			p7.setPreco(new BigDecimal(153000.00));
			p7.setTime("2021-09-14 22:22");
			p7.setSite(s2);
			p7.setTeatro(t3);
			promoDAO.save(p7);

			Promo p8 = new Promo();
			p8.setNomePeca("Peca5");
			p8.setPreco(new BigDecimal(13.90));
			p8.setTime("2021-10-23 13:15");
			p8.setSite(s2);
			p8.setTeatro(t4);
			promoDAO.save(p8);
		};
	}
}

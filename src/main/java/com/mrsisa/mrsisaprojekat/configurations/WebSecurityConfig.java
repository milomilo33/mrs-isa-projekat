package com.mrsisa.mrsisaprojekat.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.mrsisa.mrsisaprojekat.security.auth.RestAuthenticationEntryPoint;
import com.mrsisa.mrsisaprojekat.security.auth.TokenAuthenticationFilter;
import com.mrsisa.mrsisaprojekat.service.AllUserDetailsService;
import com.mrsisa.mrsisaprojekat.util.TokenUtils;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	private AllUserDetailsService userDetailsService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
		
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			// Definisemo uputstva AuthenticationManager-u:
		
			// 1. koji servis da koristi da izvuce podatke o korisniku koji zeli da se autentifikuje
			// prilikom autentifikacije, AuthenticationManager ce sam pozivati loadUserByUsername() metodu ovog servisa
			.userDetailsService(userDetailsService) 
			
			// 2. kroz koji enkoder da provuce lozinku koju je dobio od klijenta u zahtevu 
			// da bi adekvatan hash koji dobije kao rezultat hash algoritma uporedio sa onim koji se nalazi u bazi (posto se u bazi ne cuva plain lozinka)
			.passwordEncoder(passwordEncoder());
	}
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// komunikacija izmedju klijenta i servera je stateless posto je u pitanju REST aplikacija
			// ovo znaci da server ne pamti nikakvo stanje, tokeni se ne cuvaju na serveru 
			// ovo nije slucaj kao sa sesijama koje se cuvaju na serverskoj strani - STATEFULL aplikacija
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

			// sve neautentifikovane zahteve obradi uniformno i posalji 401 gresku
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

			// svim korisnicima dopusti da pristupe sledecim putanjama:
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()		// /auth/**		// /api/auth/**

			.antMatchers("/api/medicaments/all").permitAll()
			.antMatchers("/api/patients/**").permitAll()
				//.antMatchers("/api/dermatologist/examinations").permitAll() // OBRISATI!
				//.antMatchers("/api/appointments/**").permitAll() // OBRISATI!
				//.antMatchers("/api/dermatologist").hasAnyRole("DERMATOLOGIST", "PHARMACY_ADMIN", "SYSTEM_ADMIN")
			//.antMatchers("/api/pharmacist").hasAnyRole("PHARMACIST", "PHARMACY_ADMIN", "SYSTEM_ADMIN")
								
			// ukoliko ne zelimo da koristimo @PreAuthorize anotacije nad metodama kontrolera, moze se iskoristiti hasRole() metoda da se ogranici
			// koji tip korisnika moze da pristupi odgovarajucoj ruti. Npr. ukoliko zelimo da definisemo da ruti 'admin' moze da pristupi
			// samo korisnik koji ima rolu 'ADMIN', navodimo na sledeci nacin: 
			// .antMatchers("/admin").hasRole("ADMIN") ili .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
							       
			// za svaki drugi zahtev korisnik mora biti autentifikovan
			.anyRequest().authenticated().and()
		      .formLogin()
		      .loginPage("/Login.vue")
		      .loginProcessingUrl("/api/auth/login")
		      .defaultSuccessUrl("/SystemAdminPage.vue", true).and()

			
			// za development svrhe ukljuci konfiguraciju za CORS iz WebConfig klase
			.cors().and()

			// umetni custom filter TokenAuthenticationFilter kako bi se vrsila provera JWT tokena umesto cistih korisnickog imena i lozinke (koje radi BasicAuthenticationFilter)
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService), BasicAuthenticationFilter.class);
		
		// zbog jednostavnosti primera ne koristimo Anti-CSRF token (https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
		http.csrf().disable();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		// Autentifikacija ce biti ignorisana ispod navedenih putanja (kako bismo ubrzali pristup resursima)
		// Zahtevi koji se mecuju za web.ignoring().antMatchers() nemaju pristup SecurityContext-u
		
		// Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
		 web.ignoring().antMatchers(HttpMethod.POST, "/api/auth/login");
		 
		// Ovim smo dozvolili pristup statickim resursima aplikacije
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js");
	}

}

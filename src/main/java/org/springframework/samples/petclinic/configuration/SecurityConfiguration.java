package org.springframework.samples.petclinic.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				// 404 error page for demonstration
				.antMatchers("/no-controller").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/admin/**").hasAnyAuthority("admin")
				.antMatchers("/authors/**").hasAnyAuthority("author","admin")				
				.antMatchers("/vets/**").authenticated()
				// Stories
				.antMatchers("/stories/list").permitAll()
				.antMatchers("/stories/{storyId}/show").permitAll()
				.antMatchers("/stories/new").hasAnyAuthority("author")
				.antMatchers("/stories/{storyId}/edit").hasAnyAuthority("author")
				.antMatchers("/stories/{storyId}/delete").hasAnyAuthority("author")
				//Chapters
				.antMatchers("/stories/**/chapters").hasAnyAuthority("author")
				.antMatchers("/stories/**/chapters/{chapterId}").hasAnyAuthority("author")
				.antMatchers("/stories/**/chapters/new").hasAnyAuthority("author")
				.antMatchers("/stories/**/chapters/**/edit").hasAnyAuthority("author")
				.antMatchers("/stories/**/chapters/{chapterId}/delete").hasAnyAuthority("author")
				//Reviews
				.antMatchers("/stories/**/reviews/new").hasAnyAuthority("author")
				// Reports
				.antMatchers("/stories/**/chapters/{chapterId}/reports/new").hasAnyAuthority("author")
				// Contracts
				.antMatchers("/contracts/list").hasAnyAuthority("author", "company")

				//Listar contratos compania (H11)
		        .antMatchers("/contracts/company/list").hasAnyAuthority("company")
		        //Mostrar contratos compania (H11)
		        .antMatchers("/contracts/{contractId}/show").hasAnyAuthority("author","company")
		        .antMatchers("/contracts/{contractId}/accept").hasAnyAuthority("author")
		        .antMatchers("/contracts/{contractId}/reject").hasAnyAuthority("author")
          // Crear solicitud de contrato
           .antMatchers("/contracts/new").hasAnyAuthority("company")
		        /*Default mathers*/
      
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	/*.loginPage("/login")*/
				 	.failureUrl("/login-error")
				.and()
					.logout()
						.logoutSuccessUrl("/"); 
                // Configuración para que funcione la consola de administración 
                // de la BD H2 (deshabilitar las cabeceras de protección contra
                // ataques de tipo csrf y habilitar los framesets si su contenido
                // se sirve desde esta misma página.
                http.csrf().ignoringAntMatchers("/h2-console/**");
                http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}



package co.edu.iudigital.helpmeiud.auth;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


/**
 * Config recursos Server
 * si token es válido
@author RobertCastillos
@author OscarGarcia
@author JosueSanmartin
@author CamiloHenao *
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	
	// protección del lado de oath2
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		
		.antMatchers(HttpMethod.POST, "/usuarios/signup**").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/usuarios/signup**").permitAll()	
		
		.antMatchers(HttpMethod.GET, "/casos", "/casos/caso/**").permitAll()
		.antMatchers(HttpMethod.GET, "/usuarios/uploads/img/**").permitAll()

		.anyRequest().authenticated()// las rutas no especificadas, serán para usuarios autenticados
		
		.and()
		
		.cors().configurationSource(corsConfigurationSource())
		
		.and()
		.csrf().disable();
		
	}
	
	//---------------------------- 2--------------------
	//cors: croised 

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);//permitir credenciales
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));//"Content-Type", 
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);// para todas las rutas del back
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		
		return bean;
	}
}
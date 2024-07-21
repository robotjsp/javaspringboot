package co.edu.iudigital.helpmeiud.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*author RobertCastillos
@author OscarGarcia
@author JosueSanmartin
@author */
@Configuration
//@EnableGlobalMethodSecurity(securedEnabled = true)//para usar el @Secured
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // aut pre y post
@EnableWebSecurity
@EnableAutoConfiguration(
	exclude = SecurityAutoConfiguration.class
)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService usuarioService;
	
	// registramos el objeto como componente de spring
	// esta es la forma de registrar objetos que retornan métodos
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Autowired // inyectar vía argumento el auth
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
	}
	
	 // colocamos como beans para usarlo en el servidor de autorización
	 
	@Bean("authenticationManager")//  colocamos esto para que  inyectemos  este y no de otro osea para el qualifier
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	// protección del spring
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("**/swagger-ui.html").permitAll()
			
			.anyRequest().authenticated()
			.and()
			.csrf().disable()//sin proteccion de forms ataques cross por estar en capa react separado del back
			
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/*swagger*/
    @Override
    public void configure(WebSecurity web) throws Exception {
    	 web.ignoring()
        // .antMatchers( HttpMethod.GET, "/**")
         .antMatchers("/app/**/*.{js,html}")
         .antMatchers("/i18n/**")
         .antMatchers("/content/**")
         .antMatchers("/h2-console/**")
		 .antMatchers("/swagger-ui/**")
         .antMatchers("/swagger-ui/index.html")
         .antMatchers("/swagger-ui.html")
         .antMatchers("/v3/api-docs")
		 .antMatchers("/v3/api-docs/**")
         .antMatchers("/test/**");
    }

}
package co.edu.iudigital.helpmeiud.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * Process Athentication by OAuth2: login, etc.
 * eveythings related with login, Authentication with oauth2, the token is created and if the access is allow by atho, proceed with next one:
 *@author RobertCastillos
@author OscarGarcia
@author JosueSanmartin
@author CamiloHenao
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.jwt.client-service}")
    private String client;

    @Value("${security.jwt.password-service}")
    private String secret;

    @Value("${security.jwt.scope-read}")
    private String read;

    @Value("${security.jwt.scope-write}")
    private String write;

    @Value("${security.jwt.grant-password}")
    private String grantPassword;

    @Value("${security.jwt.grant-refresh}")
    private String grantRefresh;

    @Value("${security.jwt.token-validity-seconds}")
    private Integer accessTime;

    @Value("${security.jwt.refresh-validity-seconds}")
    private Integer refreshTime;

    // creamos como un bean en la clase de spring security config

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //we process login
    @Autowired
    @Qualifier("authenticationManager")// we make sure that the springsecurity is active
    private AuthenticationManager authenticationManager;


    // despues
    @Autowired
    private TokenMoreInfo tokenMoreInfo;


    // se implementan los 3 métodos de configuración


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //we add the more infomation required
        // --------2---------------------
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();// we match the token info by default and the new one,
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenMoreInfo, accessTokenConverter()));//we add both
        //-------1
        endpoints.authenticationManager(authenticationManager)// we register the autenticationManager//--- 1
                .tokenStore(tokenStore())//unrequired, but we do explicit --- 2
                .accessTokenConverter(accessTokenConverter())// we register the accesstokenconverter and create the method
                
                //we check the token validation //--- 1
                .tokenEnhancer(tokenEnhancerChain);//
    }

    /*permissions path*/
    // login path must be public (authenticator service)
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder)//---- 2
                .tokenKeyAccess("permitAll()")//permissions users anomnymus or not //---1
                .checkTokenAccess("isAuthenticated()");//review and check the token; access to endpoint that check token
        // Only acces for user authenticated //------1
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()// type of storage
                .withClient(client)// we create customer
                .secret(passwordEncoder.encode(secret))//password codifited
                .scopes(read, write)// scope: access for the App
                .authorizedGrantTypes(grantPassword, grantRefresh)//kind of token design, how will be get it (there's another ways)
                // refresh token obtiene token de acceso renovado y poder continuar en los recursos antes que caduque el token
                .accessTokenValiditySeconds(accessTime)// time validation
                .refreshTokenValiditySeconds(refreshTime);// refresh  time token
    }


 // We do create the tocken to save it
    /* pero JWtAccess trabaja con el, entonces se pone opcional
     * pero lo hacemos explicitamente
     *
     * @return
     */
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    
    // translate the token process
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //First process
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();// by default has a token storaged
        //Second process
        jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVATE);// secret passcode
        jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLIC);
        //Final process
        return new JwtAccessTokenConverter();// crea un
    }
}
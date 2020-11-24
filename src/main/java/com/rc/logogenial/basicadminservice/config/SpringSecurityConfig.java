package com.rc.logogenial.basicadminservice.config;


import com.rc.logogenial.basicadminservice.service.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;


// TODO: Auto-generated Javadoc
/**
 * The Class WebSecurityConfig.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /** The user details service. */
    @Autowired
    UsuarioService userDetailsService;

    /** The unauthorized handler. */
    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    /**
     * Authentication jwt token filter.
     *
     * @return the jwt auth token filter
     */
    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#authenticationManagerBean()
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    SecurityConfiguration security() {
//        return new SecurityConfiguration(null, null, null, // realm Needed for authenticate button to work
//                null, // appName Needed for authenticate button to work
//                "BEARER ", // apiKeyValue
//                ApiKeyVehicle.HEADER, "AUTHORIZATION_HEADER", // apiKeyName
//                null);
//    }

    /**
     * Password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/", "/webjars/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/v1/archivo-api/getById/**").permitAll()
                .antMatchers("/v1/usuario-api/create").permitAll()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers("/api/administracion/archivo/downloadReporte/**").permitAll()
//                .antMatchers("/api/mejoramiento/mantenimiento/**/exportActaPDF").permitAll()
                .antMatchers("/swagger**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable().authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "**").permitAll()
        ;

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultWebSecurityExpressionHandler;
    }
}
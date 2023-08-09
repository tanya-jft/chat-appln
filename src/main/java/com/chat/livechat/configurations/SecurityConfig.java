package com.chat.livechat.configurations;


import com.chat.livechat.service.impl.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConfig {

    private SessionRegistry sessionRegistry;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/css/**", "js/..", "/signup")
                        .permitAll().anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login").permitAll()).authenticationProvider(authenticationProvider())
                .logout((logout) -> logout.logoutSuccessUrl("/login").permitAll() )
                .sessionManagement(session -> session.maximumSessions(1).sessionRegistry(sessionRegistry))
                .build();
    }


/*    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder()
                .username("tanya")
                .password("{noop}123")
                .roles("USER").build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}123")
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/


    @Bean
    public CustomUserDetailsService detailsService() {
        return new CustomUserDetailsService();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(detailsService());
        log.info("Dao Auth provider");
        return dao;
    }


}

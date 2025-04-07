package io.nology.postcode_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http.authorizeHttpRequests((auth) ->
    // auth.anyRequest().authenticated()).httpBasic(Customizer.withDefaults())
    // .formLogin(Customizer.withDefaults());
    // return http.build();
    // }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // 	// @formatter:off
	// 	http
	// 			// .authorizeHttpRequests((authorize) -> authorize
	// 					// .anyRequest().authenticated()
	// 			// )
	// 			.httpBasic(withDefaults())
	// 			// .formLogin(withDefaults());
	// 	// @formatter:on
    // return http.build();
    // }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ...
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .httpBasic(withDefaults()).formLogin(form -> form.loginPage("/login").permitAll());
        return http.build();
    }

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    // // UserBuilder users = User.withDefaultPasswordEncoder();
    // // UserDetails user = users
    // // .username("user")
    // // .password("password")
    // // .roles("USER")
    // // .build();
    // // UserDetails admin = users
    // // .username("admin")
    // // .password("test")
    // // .roles("USER", "ADMIN")
    // // .build();
    // // System.out.println(user.getPassword());
    // // System.out.println(admin.getPassword());
    // UserDetails user =
    // User.withDefaultPasswordEncoder().username("user").password("test").roles("USER")
    // .build();
    // return new InMemoryUserDetailsManager(user);
    // }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("test")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}

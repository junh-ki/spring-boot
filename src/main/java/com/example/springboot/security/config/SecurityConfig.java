package com.example.springboot.security.config;

import com.example.springboot.security.entity.UserAccount;
import com.example.springboot.security.repository.UserManagementRepository;
import com.example.springboot.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
        userDetailsManager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build());
        return userDetailsManager;
    }
     */

    @Bean
    CommandLineRunner initUsers(UserManagementRepository userManagementRepository) {
        return args -> {
            userManagementRepository.save(new UserAccount("user",
                    "password", "ROLE_USER"));
            userManagementRepository.save(new UserAccount("admin",
                    "password", "ROLE_ADMIN"));
        };
    }

    @Bean
    UserDetailsService userService(UserRepository userRepository) {
        return username -> userRepository.findByUsername(username)
                .asUser();
    }

    /*
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
        http.formLogin();
        http.httpBasic();
        return http.build();
    }
    */

    @Bean
    SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
        /*
        http.authorizeHttpRequests()
                .requestMatchers("/resources/**", "/about", "/login")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/admin/**")
                .hasRole("ADMIN")
                .requestMatchers("/db/**")
                .access((authentication, object) -> {
                    final boolean anyMissing = Stream.of("ADMIN", "DBA")
                            .map(role -> hasRole(role)
                                    .check(authentication, object)
                                    .isGranted())
                            .filter(granted -> !granted)
                            .findAny()
                            .orElse(false);
                    return new AuthorizationDecision(!anyMissing);
                })
                .anyRequest()
                .denyAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
        */
        /**
         * 1. Everyone must log in to access anything
         * 2. The initial list of videos should only be visible to authenticated users
         * 3. Any search features should be available to authenticated users
         * 4. Only admin users can add new videos
         * 5. Any other forms of access will be disabled
         * 6. These rules should apply to both the HTML web page as well as to command-line interactions
         */
        http.authorizeHttpRequests()
                .requestMatchers("/login")
                .permitAll()
                .requestMatchers("/", "/search")
                .authenticated()
                .requestMatchers(HttpMethod.GET, "/api/**")
                .authenticated()
                .requestMatchers(HttpMethod.POST, "/new-video", "/api/**")
                .hasRole("ADMIN")
                .anyRequest()
                .denyAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();
        return http.build();
    }

}

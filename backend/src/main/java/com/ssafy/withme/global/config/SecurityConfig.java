package com.ssafy.withme.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //csrf는 웹 MVC에서 필요 -> Rest API 서버에서는 불필요하므로 미사용
                .csrf(CsrfConfigurer::disable)
                //httpBasic은 웹 MVC에서 필요 -> Rest API 서버에서는 불필요하므로 미사용
                .httpBasic(HttpBasicConfigurer::disable)
                //formLogin은 웹 MVC에서 필요 -> Rest API 서버에서는 불필요하므로 미사용
                .formLogin(FormLoginConfigurer::disable)
                //시큐리티를 적용/미적용할 url 지정
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/github/**").permitAll()
//                        .anyRequest().authenticated())
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                //세션 사용 방식 설정 -> Rest API 서버에서는 불필요하므로 미사용
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Client(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}

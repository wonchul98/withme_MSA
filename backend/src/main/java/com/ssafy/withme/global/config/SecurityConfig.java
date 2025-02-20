package com.ssafy.withme.global.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated()) // 인증된 요청만 허용
                .addFilterBefore(new CustomAuthFilter(), UsernamePasswordAuthenticationFilter.class); // 사용자 정의 필터 추가

        return http.build();
    }

    // 커스텀 필터 (헤더에 유저 정보가 있는지 확인)
    public static class CustomAuthFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            String userId = request.getHeader("X-User-Id"); // 헤더에서 유저 정보 가져오기

            if (userId == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User ID missing in header");
                return;
            }

            filterChain.doFilter(request, response); // 다음 필터로 넘김
        }
    }

//    private final CustomAuthenticationSuccessHandler successHandler;
//    private final CustomOAuth2UserService customUserService;
//    private final CustomOidcUserService customOidcUserService;
//
//    @Bean
//    @Order(1)
//    public SecurityFilterChain oauthFilterChain(HttpSecurity http) throws Exception {
//        http
//                .securityMatcher("/oauth2/authorization/*", "/login/oauth2/code/*")
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .authorizationEndpoint(authorization -> authorization
//                                .baseUri("/oauth2/authorization")
//                                .authorizationRequestRepository(authorizationRequestRepository())
//                        )
//                        .redirectionEndpoint(redirection -> redirection
//                                .baseUri("/login/oauth2/code/*"))
//                        .userInfoEndpoint(userInfo -> userInfo
//                            .userService(customUserService)
//                            .oidcUserService(customOidcUserService))
//                        .successHandler(successHandler)
//                )
//
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//
//        return http.build();
//    }
//
//    @Bean
//    @Order(2)
//    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
//        http
//                //csrf, httpBasic, formLogin, session은 API 서버에서 불필요
//                .csrf(CsrfConfigurer::disable)
//                .httpBasic(HttpBasicConfigurer::disable)
//                .formLogin(FormLoginConfigurer::disable)
//                //시큐리티를 적용/미적용할 url 지정. 로그인(토큰 재발급) 할 때만 미적용
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.POST, "/oauth2/token", "/api/workspace/simple").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/readme/{workspace_id:[0-9]+}", "/api/readme/search").permitAll()
//                        .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(Customizer.withDefaults()))
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()));
//
//        return http.build();
//    }
//
//    //로컬에서 h2 콘솔에 접근할 경우 필요
//    @Bean
//    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
//    public WebSecurityCustomizer configureH2ConsoleEnable() {
//        return web -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console());
//    }
//
//    @Bean
//    @Profile("local")
//    public SecurityFilterChain localSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                                .anyRequest().permitAll());
//        return http.build();
//    }
//
//    @Bean
//    protected CorsConfigurationSource corsConfigurationSource() {
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedOrigin("http://localhost:3000");
//        corsConfiguration.addAllowedOrigin("https://www.withme.my");
//        corsConfiguration.addAllowedOrigin("https://k11a507.p.ssafy.io");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.addAllowedHeader("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//
//        return source;
//    }
//
//    @Bean
//    protected AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
//        return new CustomOAuth2AuthorizationRequestRepository();
//    }
}

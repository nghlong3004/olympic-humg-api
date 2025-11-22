package vn.edu.humg.olympic.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.edu.humg.olympic.api.constant.APIConstant;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final WebProperties properties;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth.requestMatchers(APIConstant.API_REGISTER_PATH)
                                               .permitAll()
                                               .anyRequest()
                                               .authenticated())

            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .logout(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

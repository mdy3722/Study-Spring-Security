package StudySpringSecurity1.StudySpringSecurity1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
public class SecurityConfig {

    @Bean   // 해당 메서드의 리턴이 되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean  // SecurityFilterChain을 리턴하는 메소드를 빈에 등록하는 방식(컴포넌트 방식으로 컨테이너가 관리)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**").authenticated()   // user라는 url로 들어오면 인증이 필요하다.
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")  // manager으로 들어오는 MANAGER 인증 또는 ADMIN인증이 필요하다
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // admin으로 들어오면 ADMIN 권한이 있는 사람만 들어올 수 있다.
                        .anyRequest().permitAll()    // 그리고 나머지 url은 전부 권한을 허용해준다.
                );

        http.formLogin(form -> form     // 인증 필요 시 /login 페이지로 리다이렉트
                .loginPage("/loginForm")
                .loginProcessingUrl("/login")   // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                .defaultSuccessUrl("/")  // /loginForm을 요청해서 로그인을 하면 메인페이지 이동 -> 특정 페이지에서 로그인을 하면 X
        );
        return http.build();
    }

}

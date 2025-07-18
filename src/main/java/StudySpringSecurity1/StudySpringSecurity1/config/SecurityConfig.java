package StudySpringSecurity1.StudySpringSecurity1.config;

import StudySpringSecurity1.StudySpringSecurity1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)    // secured 어노테이션 활성화, @PreAuthorize와 @PostAuthorize 어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserSerivce;

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
        )
        .oauth2Login(oauth2 -> oauth2
                .loginPage("/loginForm")   // 구글 로그인이 완료된 뒤의 후처리가 필요함 1. 코드받기(인증이 되었다는 것) -> 2. 액세스 토큰 받기(사용자 정보에 접근할 수 있는 권한이 생김) 3. 사용자 프로필 정보를 가져오고 4-1. 그 정보를 토대로 회원가입을 자동으로 진행시키기도 함, 또는 4-2. 추가적인 사용자 정보가 필요하다면 자동으로 회원가입을 시키는 것이 아니라 추가적인 회원가입 창이 나와야..
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(principalOauth2UserSerivce)
                )
        );                                  // 구글 로그인이 완료가 되면 코드를 받는 것이 아니라 (액세스토큰+사용자 정보)를 한방에 받는다.

        return http.build();
    }

}


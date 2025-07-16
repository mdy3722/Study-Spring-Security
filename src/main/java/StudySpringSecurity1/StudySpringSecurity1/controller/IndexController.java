package StudySpringSecurity1.StudySpringSecurity1.controller;

import StudySpringSecurity1.StudySpringSecurity1.entity.User;
import StudySpringSecurity1.StudySpringSecurity1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller   // view를 리턴
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache (suffix)
        return "index";   // 얘는 결과적으로 src/main/resources/templates/index.mustache를 찾는다. => 우리가 생성한 것은 index.html 파일인데 이는 WebMvcConfig.java에서 설정한다.
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 시큐리티 설정이 별도로 없으면 이 매핑은 스프링시큐리티 해당주소로 낚아채짐
    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }

    @GetMapping("/signupForm")
    public String signupForm() {
        return "signupForm";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 근데 이러면 비밀번호가 1234 => 시큐리티로 로그인을 할 수 없음. 이유는 패스워드가 암호화가 안되어 있음
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")   // 특정 메소드 호출 시 해당 사용자가 특정 권한을 갖고 있어야 접근 허용 => 없으면 403
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")   // 얘는 특정 메소드가 실행되기 "직전"에 실행됨 => @Secured와 큰 차이는 없음. 표현식의 차이 + 더 복합 조건, 권한 체크 시 사용
    // @PostAuthorize() // 메소드 호출 이후에 실행
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터정보";
    }


}

package StudySpringSecurity1.StudySpringSecurity1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller   // view를 리턴
public class IndexController {
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
    @GetMapping("/login")
    public @ResponseBody String login() {
        return "login";
    }

    @GetMapping("/signup")
    public @ResponseBody String signup() {
        return "signup";
    }

    @GetMapping("/signupProc")
    public @ResponseBody String signupProc() {
        return "회원가입 완료";
    }
}

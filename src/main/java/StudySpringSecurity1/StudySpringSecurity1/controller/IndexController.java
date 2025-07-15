package StudySpringSecurity1.StudySpringSecurity1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller   // view를 리턴
public class IndexController {
    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache (suffix)
        return "index";   // 얘는 결과적으로 src/main/resources/templates/index.mustache를 찾는다. => 우리가 생성한 것은 index.html 파일인데 이는 WebMvcConfig.java에서 설정한다.
    }
}

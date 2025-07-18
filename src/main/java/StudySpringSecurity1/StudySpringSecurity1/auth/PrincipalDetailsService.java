package StudySpringSecurity1.StudySpringSecurity1.auth;

import StudySpringSecurity1.StudySpringSecurity1.entity.User;
import StudySpringSecurity1.StudySpringSecurity1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// 시큐리티 설정에서 loginProcessingUrl("/login")으로 했었음
// 그래서 /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행이 됨. <- 이건 규칙입니다.
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 여기서 리턴되는 UserDetials 값이 Authentication에 들어감 => 그럼 그 Authentication이 시큐리티 세션(Security ContextHolder에 들어간다.
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }
        return null;
    }

}

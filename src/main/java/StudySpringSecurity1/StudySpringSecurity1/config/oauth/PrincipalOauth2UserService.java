package StudySpringSecurity1.StudySpringSecurity1.config.oauth;

import StudySpringSecurity1.StudySpringSecurity1.auth.PrincipalDetails;
import StudySpringSecurity1.StudySpringSecurity1.entity.User;
import StudySpringSecurity1.StudySpringSecurity1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired    // 이미 회원가입 되어 있을 수 있으니 UserRepository를 DI해서 확인
    private UserRepository userRepository;

    // 구글로부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthorizationException {
        System.out.println("userRequest"+userRequest.getClientRegistration());
        System.out.println("userRequest"+userRequest.getAccessToken().getTokenValue());

        OAuth2User oauth2User = super.loadUser(userRequest);

        System.out.println("userRequest"+oauth2User.getAttributes());

        // 강제로 회원가입 진행
        String provider = userRequest.getClientRegistration().getClientId();  // google
        String providerId = oauth2User.getAttribute("sub");
        String username = provider+"_"+providerId;  // 중복 방지
        String email = oauth2User.getAttribute("email");
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }


}

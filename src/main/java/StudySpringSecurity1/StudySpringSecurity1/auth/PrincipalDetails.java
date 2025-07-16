package StudySpringSecurity1.StudySpringSecurity1.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (Security ContextHolder라는 키값을 세션 정보에 저장시킴)
// 이 세션에 들어가는 정보 : Authentication 타입의 객체
// 이 Authentication 내부에는? User 정보가 있음
// 이 User 객체의 타입 : UserDetails 타입 객체

// 시큐리티 세션 => Authentication 객체 저장 => 이때의 user 정보 타입은 UserDetails => UserDetails(PrincipalDetails)

import StudySpringSecurity1.StudySpringSecurity1.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }


    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 언제 사용하는가
        // 우리 사이트에서 1년 동안 회원이 로그인을 안하면 휴먼계정으로 전환하는 정책이 있다면
        // user.getLoginDate()등을 가져와서 "현재시간-로그인시간" => 1년 초과하면 return false
        return true;
    }
}

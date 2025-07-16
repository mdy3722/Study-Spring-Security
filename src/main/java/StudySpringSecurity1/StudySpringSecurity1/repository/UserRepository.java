package StudySpringSecurity1.StudySpringSecurity1.repository;

import StudySpringSecurity1.StudySpringSecurity1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 JpaRepository가 들고 있음.
// @Repository라는 어노테이션 없어도 IoC된다. 이유는 JpaRepository를 상속했기 때문에
public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA 쿼리메소드 참고
    // "findBy"까지는 기본 규칙 -> 뒤에 username은 문법
    // select * from user where username = 1?
    public User findByUsername(String username);   // 여기서 Integer는 User 모델의 pk

//    // select * from user where email = ?
//    public User findByEmail();

}

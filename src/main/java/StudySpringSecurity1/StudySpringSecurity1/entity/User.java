package StudySpringSecurity1.StudySpringSecurity1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String username;

    private String password;

    private String email;

    private String role;     // ROLE_USER, ROLE_ADMIN

    // 일반 로그인 회원인지, oauth 로그인 회원인지 구분을 위해
    private String provider;   // 들어갈 값 : google
    private String providerId;    // 들어갈 값 : google_{sub값}

    @CreationTimestamp
    private Timestamp createDate;

    @Builder
    public User(String username, String email, String password, String role, String provider, String providerId, Timestamp createDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}

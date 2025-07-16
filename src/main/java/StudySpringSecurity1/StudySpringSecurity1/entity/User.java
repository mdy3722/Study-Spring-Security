package StudySpringSecurity1.StudySpringSecurity1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String username;

    private String password;

    private String email;

    private String role;     // ROLE_USER, ROLE_ADMIN

    @CreationTimestamp
    private Timestamp createDate;

}

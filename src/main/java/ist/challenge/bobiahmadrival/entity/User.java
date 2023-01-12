package ist.challenge.bobiahmadrival.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "m_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}

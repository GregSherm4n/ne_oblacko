package com.neoblacko.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "userPassword")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_login")
    private String userLogin;

    @Column(name = "user_password", updatable = false)
    private String userPassword;

    @Column(name = "user_role")
    private String userRole;

    @ManyToOne
    @JoinColumn(name = "user_tariff")
    private Tariff tariff;

    @OneToMany(mappedBy = "user")
    private List<UserFile> files;

}

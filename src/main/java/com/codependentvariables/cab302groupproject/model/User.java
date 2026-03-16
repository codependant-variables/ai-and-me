package com.codependentvariables.cab302groupproject.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @Column(unique = true)
    private String email;

    @Builder
    public User(String name, String email) {
        setName(name);
        setEmail(email);
    }

    public void setName(String s) {
        if (!validateName(s))
            throw new IllegalArgumentException();

        this.name = s;
    }

    public static boolean validateName(String s) {
        return s != null && !s.isEmpty();
    }

    public void setEmail(String s) {
        if (!validateEmail(s))
            throw new IllegalArgumentException();

        this.email = s;
    }

    public static boolean validateEmail(String s) {
        if (s == null)
            return false;

        int atIndex = s.indexOf('@');
        if (atIndex <= 0)
            return false;

        int dotIndex = s.indexOf('.', atIndex);
        if (dotIndex <= atIndex + 1 || dotIndex >= s.length() - 1)
            return false;

        return true;
    }
}
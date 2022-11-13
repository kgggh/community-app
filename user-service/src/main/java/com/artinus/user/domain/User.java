package com.artinus.user.domain;

import com.artinus.user.exception.UserCommonException;
import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.UUID;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)", name = "user_id")
    private UUID id;

    @Column(length = 20, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    private User(String email, String password) {
        validCheck(email);
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
    }


    public static User newInstance(String email, String password) {
        return new User(email, password);
    }

    private void validCheck(String email) {
        if(Strings.isBlank(email)) {
            throw new UserCommonException("[Invalid] Email is not null and blank");
        }

        if(email.length() > 20) {
            throw new UserCommonException("[Invalid] Email length cannot exceed 20 characters ");
        }
    }
}

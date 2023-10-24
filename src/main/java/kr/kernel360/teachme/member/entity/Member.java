package kr.kernel360.teachme.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "member")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Convert(converter = MemberTypeConverter.class)
    @Column(columnDefinition = "VARCHAR(20)")
    private MemberType memberType;

    private String password;

    private String userName;

    @Builder
    public Member(String email, MemberType memberType, String password, String userName) {
        Assert.hasLength(email, "Member email must not be empty");
        this.email = email;
        this.password = password;
        Assert.hasLength(userName, "Member user name must not be empty");
        this.userName = userName;
    }
}

package kr.kernel.teachme.member.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberTest {

    @Test
    public void createWhenEmailIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Member(null, MemberType.ofCode(1), "password", "userName"))
                .withMessage("Member email must not be empty");
    }

    @Test
    public void createWhenEmailIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Member("", MemberType.ofCode(1), "password", "userName"))
                .withMessage("Member email must not be empty");
    }

    @Test
    public void createWhenUserNameIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Member("email", MemberType.ofCode(1), "password", null))
                .withMessage("Member user name must not be empty");
    }

    @Test
    public void createWhenUserNameIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Member("email", MemberType.ofCode(1), "password", ""))
                .withMessage("Member user name must not be empty");
    }

}

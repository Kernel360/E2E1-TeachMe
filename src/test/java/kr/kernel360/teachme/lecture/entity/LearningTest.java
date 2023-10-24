package kr.kernel360.teachme.lecture.entity;

import kr.kernel360.teachme.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearningTest {
    @Test
    public void createWhenLectureIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Learning(null, new Member()))
                .withMessage("Learning lecture must not be null");
    }

    @Test
    public void createWhenMemberIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Learning(new Lecture(), null))
                .withMessage("Learning member must not be null");
    }
}

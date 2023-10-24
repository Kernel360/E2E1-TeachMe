package kr.kernel360.teachme.review.entity;

import kr.kernel360.teachme.lecture.entity.Lecture;
import kr.kernel360.teachme.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewTest {

    @Test
    public void createWhenMemberIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Review(null, new Lecture(), 1.0d, "content"))
                .withMessage("Review member must not be null");
    }

    @Test
    public void createWhenLectureIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Review(new Member(), null, 1.0d, "content"))
                .withMessage("Review lecture must not be null");
    }

    @Test
    public void createWhenContentIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Review(new Member(), new Lecture(), 1.0d, null))
                .withMessage("Review content must not be empty");
    }

    @Test
    public void createWhenContentIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Review(new Member(), new Lecture(), 1.0d, ""))
                .withMessage("Review content must not be empty");
    }
}

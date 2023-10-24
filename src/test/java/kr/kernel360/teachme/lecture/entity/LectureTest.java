package kr.kernel360.teachme.lecture.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LectureTest {

    @Test
    public void createWhenUniqueIdIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Lecture(null, new Platform(), "teacher", "url", "imageUrl", new Category(), BigDecimal.ONE))
                .withMessage("Lecture unique id must not be empty");
    }

    @Test
    public void createWhenUniqueIdIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Lecture("", new Platform(), "teacher", "url", "imageUrl", new Category(), BigDecimal.ONE))
                .withMessage("Lecture unique id must not be empty");
    }

    @Test
    public void createWhenPlatformIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Lecture("uniqueId", null, "teacher", "url", "imageUrl", new Category(), BigDecimal.ONE))
                .withMessage("Lecture platform must not be null");
    }

}

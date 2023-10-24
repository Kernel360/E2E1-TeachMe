package kr.kernel360.teachme.lecture.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlatformTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createWhenNameIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Platform(null))
                .withMessage("Platform name must not be empty");
    }

    @Test
    public void createWhenNameIsEmptyThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Platform(""))
                .withMessage("Platform name must not be empty");
    }

    @Test
    public void saveShouldPersistData() {
        Platform platform = this.entityManager.persistFlushFind(new Platform("name"));
        assertThat(platform.getName()).isEqualTo("name");
    }
}

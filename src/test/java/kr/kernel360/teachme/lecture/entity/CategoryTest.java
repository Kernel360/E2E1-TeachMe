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
public class CategoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createWhenNameIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Category(null))
                .withMessage("Category name must not be empty");
    }

    @Test
    public void createWhenNameIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Category(""))
                .withMessage("Category name must not be empty");
    }

    @Test
    public void saveShouldPersistData() {
        Category category = this.entityManager.persistFlushFind(new Category("name"));
        assertThat(category.getName()).isEqualTo("name");
    }
}

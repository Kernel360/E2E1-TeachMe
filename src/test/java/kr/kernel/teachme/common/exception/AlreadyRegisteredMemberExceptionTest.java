package kr.kernel.teachme.common.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AlreadyRegisteredMemberExceptionTest {

    @Test
    void testExceptionWithCustomMessage() {
        String customMessage = "Custom error message";
        AlreadyRegisteredMemberException exception = new AlreadyRegisteredMemberException(customMessage);

        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithDefaultMessage() {
        AlreadyRegisteredMemberException exception = new AlreadyRegisteredMemberException();

        assertEquals("이미 등록된 유저입니다.", exception.getMessage());
    }
}